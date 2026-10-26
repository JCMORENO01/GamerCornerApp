package com.example.gamercornerapp.ui.Screens.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    private var reviewsJob: Job? = null

    // Función para cargar el perfil de un usuario
    fun loadUserProfile(userId: String, forceReload: Boolean = false) {
        if (!forceReload && currentProfileId == userId && _uiState.value.userProfile != null) return
        currentProfileId = userId

        reviewsJob?.cancel()

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

            if (userResult.isSuccess) {
                val userProfile = userResult.getOrNull()

                if (userProfile != null) {
                    val baseProfile = userProfile

                    // Suscribirse al flujo de reseñas manteniendo isLoading = true hasta tenerlas listas
                    reviewsJob = viewModelScope.launch {
                        reviewRepository.listenReviewsByUser(userId)
                            .catch { error ->
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = error.localizedMessage
                                            ?: "Error al cargar reseñas"
                                    )
                                }
                            }
                            .collect { reviews ->
                                val updatedProfile = baseProfile.copy(
                                    stats = baseProfile.stats.copy(
                                        reviewsCount = reviews.size
                                    )
                                )
                                _uiState.update { state ->
                                    state.copy(
                                        userProfile = updatedProfile,
                                        reviews = reviews,
                                        currentUserId = currentUserId,
                                        isLoading = false,
                                        errorMessage = null
                                    )
                                }
                            }
                    }
                }
            } else {
                val error = userResult.exceptionOrNull()

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

        // Actualización optimista de la UI
        val currentProfile = _uiState.value.userProfile
        if (currentProfile != null) {
            val newIsFollowed = !currentProfile.isFollowed
            val newFollowersCount = if (newIsFollowed) {
                currentProfile.stats.followersCount + 1
            } else {
                (currentProfile.stats.followersCount - 1).coerceAtLeast(0)
            }

            _uiState.update { state ->
                state.copy(
                    userProfile = currentProfile.copy(
                        isFollowed = newIsFollowed,
                        stats = currentProfile.stats.copy(
                            followersCount = newFollowersCount
                        )
                    )
                )
            }
        }

        viewModelScope.launch {
            try {
                val result = userRepository.toggleFollowUser(
                    currentUserId,
                    targetUserId
                )

                if (result.isSuccess) {
                    loadUserProfile(targetUserId, forceReload = true)
                } else {
                    // Revertir en caso de fallo
                    if (currentProfile != null) {
                        _uiState.update { state ->
                            state.copy(userProfile = currentProfile)
                        }
                    }
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
