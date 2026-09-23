package com.example.gamercornerapp.ui.Screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
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

        if (currentState.email.isEmpty() || currentState.password.isEmpty() || currentState.fullName.isEmpty()) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_all_fields_required 
                )
            }
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.update {
                it.copy(
                    showError = true,
                    errorRes = R.string.error_invalid_email
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

        if (!currentState.termsAccepted) {
            _uiState.update { 
                it.copy(
                    showError = true, 
                    errorRes = R.string.error_accept_terms 
                ) 
            }
            return
        }

        viewModelScope.launch {
            val result = authRepository.signUp(currentState.email, currentState.password)
            result.onSuccess {
                _uiState.update { it.copy(showError = false, navigateToFeed = true) }
            }.onFailure { error ->
                val errorRes = when (error) {
                    is com.google.firebase.auth.FirebaseAuthUserCollisionException ->
                        R.string.error_email_already_registered
                    is com.google.firebase.auth.FirebaseAuthWeakPasswordException ->
                        R.string.error_password_too_short
                    is com.google.firebase.FirebaseNetworkException ->
                        R.string.error_network
                    else ->
                        R.string.error_generic_auth
                }
                _uiState.update { 
                    it.copy(
                        showError = true, 
                        errorRes = errorRes
                    ) 
                }
            }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToFeed = false) }
    }
}
