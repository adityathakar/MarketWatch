plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.appsworld.marketwatch.core.state"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
}
