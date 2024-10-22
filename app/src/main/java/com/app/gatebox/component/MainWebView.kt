package com.app.gatebox.component

import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MainWebView() {
  val context = LocalContext.current
  val webView = remember { WebView(context) }

  AndroidView(
    factory = {
      webView.apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addJavascriptInterface(JSKotlinBridge(), "webview-layla")
      }
    }
  )
}