package com.app.gatebox

import com.app.gatebox.common.BaseViewModel

class MainViewModel: BaseViewModel<WebUiState>() {

  fun turnOnAlarm() {
    setState { copy(alarmState = true) }
  }

  fun turnOffAlarm() {
    setState { copy(alarmState = true) }
  }

}