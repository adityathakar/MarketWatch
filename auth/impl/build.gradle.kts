import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
}

val localProps = Properties()
rootProject.file("local.properties").inputStream().use { localProps.load(it) }

android {
    namespace = "com.appsworld.marketwatch.auth"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "KITE_API_KEY",         "\"${localProps.getProperty("KITE_API_KEY")}\"")
        buildConfigField("String", "WORKER_SECRET",        "\"${localProps.getProperty("KITE_WORKER_SECRET")}\"")
        buildConfigField("String", "KITE_AUTH_WORKER_URL", "\"${localProps.getProperty("KITE_AUTH_WORKER_URL")}\"")
        buildConfigField("String", "KITE_REDIRECT_URL",    "\"${localProps.getProperty("KITE_REDIRECT_URL")}\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(project(":auth:api"))
    implementation(project(":core:navigation"))
    implementation(project(":core:infra"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}
