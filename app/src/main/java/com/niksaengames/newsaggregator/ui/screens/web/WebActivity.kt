package com.niksaengames.newsaggregator.ui.screens.web

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.niksaengames.newsaggregator.ui.screens.main.MainActivity
import com.niksaengames.newsaggregator.ui.theme.NewsAggregatorTheme

class WebActivity : ComponentActivity() {

    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        url = intent.getStringExtra("url")?:""
        setContent {
            NewsAggregatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScrollableWebView(url)
                }
            }
        }

        onBackPressedDispatcher.addCallback{
            val intent = Intent(this@WebActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Composable
    fun ScrollableWebView(url: String) {
        val context = LocalContext.current
        val webView = remember {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                isVerticalScrollBarEnabled = true
                isHorizontalScrollBarEnabled = true
                scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
                webViewClient = WebViewClient()
            }
        }

        AndroidView(
            factory = { webView },
            modifier = Modifier
                .fillMaxSize()
        ) { view ->
            view.loadUrl(url)
        }
    }
}