package com.example.gamercornerapp.ui.Screens.selfProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.StorageRepository
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
class SelfProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SelfProfileState())
    val uiState: StateFlow<SelfProfileState> = _uiState.asStateFlow()

    private var userJob: Job? = null
    private var reviewsJob: Job? = null

    init {
        loadSelfProfile()
    }

    fun loadSelfProfile() {
        val currentUserId = authRepository.currentUser?.uid
        if (currentUserId == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Usuario no autenticado. Por favor inicia sesión."
                )
            }
            return
        }

        userJob?.cancel()
        reviewsJob?.cancel()

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        // Escuchar cambios en el perfil del usuario en tiempo real (por ejemplo, biografía)
        userJob = viewModelScope.launch {
            userRepository.listenUserById(currentUserId)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage ?: "No se pudo obtener el usuario"
                        )
                    }
                }
                .collect { userProfile ->
                    var updatedProfile = userProfile
                    val currentPhotoUrl = authRepository.currentUser?.photoUrl?.toString()
                    if (currentPhotoUrl != null) {
                        updatedProfile = updatedProfile.copy(profilePictureUrl = currentPhotoUrl)
                    }

                    _uiState.update { state ->
                        state.copy(
                            userProfile = updatedProfile.copy(
                                stats = updatedProfile.stats.copy(
                                    reviewsCount = state.reviews.size
                                )
                            ),
                            errorMessage = null
                        )
                    }
                }
        }

        // Escuchar reseñas del usuario en tiempo real y ocultar el loading al tener todo listo
        reviewsJob = viewModelScope.launch {
            reviewRepository.listenReviewsByUser(currentUserId)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage
                        )
                    }
                }
                .collect { reviews ->
                    _uiState.update { state ->
                        val currentProf = state.userProfile
                        state.copy(
                            userProfile = currentProf?.copy(
                                stats = currentProf.stats.copy(
                                    reviewsCount = reviews.size
                                )
                            ),
                            reviews = reviews,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)
            if (result.isFailure) {
                _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.localizedMessage) }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun onLogoutClick() {
        authRepository.signOut()
    }

    fun uploadImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val currentUserId = authRepository.currentUser?.uid ?: return@launch
            _uiState.update { it.copy(isLoadingImage = true, errorMessage = null) }
            val uploadResult = storageRepository.uploadProfileImage(uri)
            if (uploadResult.isSuccess) {
                val downloadUrl = uploadResult.getOrNull()!!
                authRepository.updateProfilePicture(downloadUrl)
                
                val currentProfile = _uiState.value.userProfile
                if (currentProfile != null) {
                    userRepository.updateUserProfile(
                        id = currentUserId,
                        username = currentProfile.username,
                        bio = currentProfile.bio,
                        birthDate = currentProfile.birthDate,
                        profilePictureUrl = downloadUrl
                    )
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoadingImage = false,
                        userProfile = currentState.userProfile?.copy(
                            profilePictureUrl = downloadUrl
                        )
                    )
                }
            } else {
                val error = uploadResult.exceptionOrNull()
                val errorMsg = if (error?.message?.contains("unauthenticated", ignoreCase = true) == true) {
                    "Error: Usuario no autenticado. Inicia sesión nuevamente."
                } else if (error?.message?.contains("quota", ignoreCase = true) == true || error?.message?.contains("plan", ignoreCase = true) == true) {
                    "Error de almacenamiento: Se requiere configurar el plan de Firebase Storage."
                } else {
                    "Error al subir imagen: ${error?.localizedMessage ?: "Verifica tu conexión e intenta de nuevo."}"
                }
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoadingImage = false,
                        errorMessage = errorMsg
                    )
                }
            }
        }
    }

    fun onErrorDismiss() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
