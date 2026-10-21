
package com.example.gamercornerapp.ui.Screens.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileState())

    val uiState: StateFlow<UserProfileState> =
        _uiState.asStateFlow()

    private var currentProfileId: String? = null
    private var isUpdatingFollow = false

    // Función para cargar el perfil de un usuario
    fun loadUserProfile(userId: String) {
        currentProfileId = userId

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val currentUserId = authRepository.currentUser?.uid

            val userResult = userRepository.getUserWithFollowState(
                userId,
                currentUserId
            )

            val reviewsResult = reviewRepository
                .getReviewsByUser(userId)

            if (userResult.isSuccess && reviewsResult.isSuccess) {
                val userProfile = userResult.getOrNull()
                val reviews = reviewsResult.getOrNull()

                if (userProfile != null && reviews != null) {
                    _uiState.update {
                        it.copy(
                            userProfile = userProfile.copy(
                                stats = userProfile.stats.copy(
                                    reviewsCount = reviews.size
                                )
                            ),
                            reviews = reviews,
                            currentUserId = currentUserId,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } else {
                val error = userResult.exceptionOrNull()
                    ?: reviewsResult.exceptionOrNull()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error?.localizedMessage
                            ?: "No se pudo cargar el perfil"
                    )
                }
            }
        }
    }

    // Función para seguir o dejar de seguir
    fun toggleFollowUser() {
        val currentUserId = authRepository.currentUser?.uid
        val targetUserId = currentProfileId

        if (currentUserId == null || targetUserId == null) {
            _uiState.update {
                it.copy(errorMessage = "Usuario no autenticado")
            }
            return
        }

        if (currentUserId == targetUserId) return
        if (isUpdatingFollow) return

        isUpdatingFollow = true

        viewModelScope.launch {
            try {
                val result = userRepository.toggleFollowUser(
                    currentUserId,
                    targetUserId
                )

                if (result.isSuccess) {
                    loadUserProfile(targetUserId)
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudo actualizar el seguimiento"
                        )
                    }
                }
            } finally {
                isUpdatingFollow = false
            }
        }
    }

    fun onErrorDismiss() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
