package com.example.gamercornerapp.ui.Screens.register

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onFullNameChange(fullName: String) {
        _uiState.update { it.copy(fullName = fullName) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onBirthDateChange(birthDate: String) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }
    }

    fun onTermsChange(accepted: Boolean) {
        _uiState.update { it.copy(termsAccepted = accepted) }
    }

    fun onRegisterClick() {
        val currentState = _uiState.value
        
        // Simulación de las reglas de negocio del video
        if (currentState.email.isEmpty() || currentState.password.isEmpty() || currentState.fullName.isEmpty()) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_all_fields_required 
                ) 
            }
            return
        }

        if (currentState.password.length < 6) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_password_too_short 
                ) 
            }
            return
        }

        if (currentState.email == "admin@admin.com") {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_email_already_registered 
                ) 
            }
            return
        }

        if (!currentState.termsAccepted) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_accept_terms 
                ) 
            }
            return
        }

        // Si pasa todo
        _uiState.update { it.copy(showError = false, navigateToFeed = true) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToFeed = false) }
    }
}