package com.app.gatebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.gatebox.component.MainWebView
import com.app.gatebox.ui.theme.GateBoxTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GateBoxTheme {
        MainWebView()
      }
    }
  }
}