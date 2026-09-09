package com.example.gamercornerapp.ui.Screens.recoverPassword

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RecoverPasswordState())
    val uiState: StateFlow<RecoverPasswordState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }
}