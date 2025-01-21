package com.app.gatebox.component

import android.webkit.JavascriptInterface
import com.app.gatebox.MainViewModel

class JSKotlinBridge {
  private val viewModel = MainViewModel()
  companion object{
    const val NAME = "MobileGateBox"
  }

  @JavascriptInterface
  fun onAlarmClicked(turnOn: Boolean) {
    if (turnOn) viewModel.turnOnAlarm()
    else viewModel.turnOffAlarm()
  }

}