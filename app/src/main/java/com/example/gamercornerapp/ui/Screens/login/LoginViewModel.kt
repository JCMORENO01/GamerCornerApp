package com.example.gamercornerapp.ui.Screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.example.gamercornerapp.R

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun onAnimationFinished() {
        _uiState.update { it.copy(isLogoUp = true) }
    }

    fun onLoginClick() {
        val currentState = _uiState.value
        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_all_fields_required
                ) 
            }
        } else {
            _uiState.update { it.copy(showError = false, navigateToFeed = true) }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToFeed = false) }
    }
}