
package com.example.gamercornerapp.ui.Screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
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
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun onBirthDateChange(birthDate: String) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(showPassword = !it.showPassword)
        }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update {
            it.copy(
                showConfirmPassword = !it.showConfirmPassword
            )
        }
    }

    fun onTermsChange(accepted: Boolean) {
        _uiState.update {
            it.copy(termsAccepted = accepted)
        }
    }

    fun onRegisterClick() {

        val currentState = _uiState.value

        if (
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.fullName.isBlank() ||
            currentState.username.isBlank()
        ) {
            showError(R.string.error_all_fields_required)
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(
                currentState.email
            ).matches()
        ) {
            showError(R.string.error_invalid_email)
            return
        }

        if (currentState.password.length < 6) {
            showError(R.string.error_password_too_short)
            return
        }

        if (currentState.password !=
            currentState.confirmPassword
        ) {
            showError(R.string.error_generic_auth)
            return
        }

        if (!currentState.termsAccepted) {
            showError(R.string.error_accept_terms)
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    showError = false,
                    errorRes = null,
                    navigateToFeed = false
                )
            }

            val authResult = authRepository.signUp(
                currentState.email,
                currentState.password
            )

            if (authResult.isFailure) {
                handleError(authResult.exceptionOrNull())
                return@launch
            }

            val userId = authRepository.currentUser?.uid

            if (userId == null) {
                showError(R.string.error_generic_auth)
                return@launch
            }

            val userResult = userRepository.createUserProfile(
                id = userId,
                username = currentState.fullName,
                nickName = currentState.username,
                bio = null,
                birthDate = currentState.birthDate
            )

            if (userResult.isSuccess) {

                _uiState.update {
                    it.copy(
                        showError = false,
                        errorRes = null,
                        navigateToFeed = true
                    )
                }

            } else {

                handleError(userResult.exceptionOrNull())
            }
        }
    }

    private fun showError(errorRes: Int) {
        _uiState.update {
            it.copy(
                showError = true,
                errorRes = errorRes,
                navigateToFeed = false
            )
        }
    }

    private fun handleError(error: Throwable?) {

        val errorRes = when (error) {

            is FirebaseAuthUserCollisionException ->
                R.string.error_email_already_registered

            is FirebaseAuthWeakPasswordException ->
                R.string.error_password_too_short

            is FirebaseNetworkException ->
                R.string.error_network

            else ->
                R.string.error_generic_auth
        }

        showError(errorRes)
    }

    fun onNavigationHandled() {
        _uiState.update {
            it.copy(navigateToFeed = false)
        }
    }
}
