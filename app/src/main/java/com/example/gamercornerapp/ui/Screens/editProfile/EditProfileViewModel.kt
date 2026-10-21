
package com.example.gamercornerapp.ui.Screens.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState: StateFlow<EditProfileState> =
        _uiState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val currentUserId =
                authRepository.currentUser?.uid

            if (currentUserId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Debes iniciar sesión"
                    )
                }
                return@launch
            }

            val result =
                userRepository.getUserById(currentUserId)

            if (result.isSuccess) {
                val user = result.getOrNull()

                if (user != null) {
                    _uiState.update {
                        it.copy(
                            username = user.username,
                            bio = user.bio,
                            birthDate = user.birthDate ?: "",
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Perfil no encontrado"
                        )
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage =
                            result.exceptionOrNull()?.localizedMessage
                    )
                }
            }
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun onBioChange(bio: String) {
        _uiState.update {
            it.copy(bio = bio)
        }
    }

    fun onBirthDateChange(birthDate: String) {
        _uiState.update {
            it.copy(birthDate = birthDate)
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val currentUserId =
                authRepository.currentUser?.uid

            if (currentUserId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Debes iniciar sesión"
                    )
                }
                return@launch
            }

            val currentState = _uiState.value

            val result =
                userRepository.updateUserProfile(
                    id = currentUserId,
                    username = currentState.username,
                    bio = currentState.bio,
                    birthDate = currentState.birthDate
                )

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage =
                            result.exceptionOrNull()?.localizedMessage
                    )
                }
            }
        }
    }

    fun onErrorDismiss() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
