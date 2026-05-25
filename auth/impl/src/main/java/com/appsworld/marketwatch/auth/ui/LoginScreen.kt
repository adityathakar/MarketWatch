package com.appsworld.marketwatch.auth.ui

import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appsworld.marketwatch.auth.BuildConfig
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    BackHandler(enabled = webViewRef?.canGoBack() == true) {
        webViewRef?.goBack()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Connect Zerodha") }) }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewRef = this
                        settings.javaScriptEnabled = true
                        CookieManager.getInstance().setAcceptCookie(true)
                        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView,
                                request: WebResourceRequest,
                            ): Boolean {
                                val url = request.url.toString()
                                if (url.startsWith(BuildConfig.KITE_REDIRECT_URL)) {
                                    val uri = Uri.parse(url)
                                    val token = uri.getQueryParameter("request_token")
                                    val status = uri.getQueryParameter("status")
                                    if (status == "success" && token != null) {
                                        viewModel.onRequestTokenReceived(token)
                                    } else {
                                        viewModel.onLoginError()
                                    }
                                    return true
                                }
                                return false
                            }
                        }
                        loadUrl(
                            "https://kite.zerodha.com/connect/login" +
                                "?api_key=${BuildConfig.KITE_API_KEY}&v=3"
                        )
                    }
                }
            )

            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            if (uiState is LoginUiState.Error) {
                AlertDialog(
                    onDismissRequest = { viewModel.onErrorDismissed() },
                    title = { Text("Login Failed") },
                    text = { Text((uiState as LoginUiState.Error).message) },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onErrorDismissed() }) { Text("Ok") }
                    }
                )
            }
        }
    }
}
