package com.example.gamercornerapp.ui.Screens.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
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

        viewModelScope.launch {
            val result = authRepository.signIn(currentState.email, currentState.password)
            result.onSuccess {
                _uiState.update { it.copy(showError = false, navigateToFeed = true) }
            }.onFailure { error ->
                val errorRes = when (error) {
                    is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException ->
                        R.string.error_invalid_credentials
                    is com.google.firebase.auth.FirebaseAuthInvalidUserException ->
                        R.string.error_user_not_found
                    is com.google.firebase.FirebaseNetworkException ->
                        R.string.error_network
                    else ->
                        R.string.error_invalid_credentials
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
