package com.app.gatebox.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<uiState : UiState> : ViewModel() {
  private val _uiState = MutableStateFlow<uiState?>(null)
  val uiState = _uiState.asStateFlow()

  protected fun setState(stateChanger: uiState.() -> uiState) {
    _uiState.update { it?.stateChanger() }
  }
}