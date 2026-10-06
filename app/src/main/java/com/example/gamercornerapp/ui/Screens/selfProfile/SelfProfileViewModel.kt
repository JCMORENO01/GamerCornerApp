package com.example.gamercornerapp.ui.Screens.selfProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.StorageRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        loadSelfProfile()
    }

    fun loadSelfProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userResult = userRepository.getUserById("1")
            val reviewsResult = reviewRepository.getReviewsByUser("1")

            if (userResult.isSuccess && reviewsResult.isSuccess) {
                var userProfile = userResult.getOrNull()
                val reviews = reviewsResult.getOrNull()

                val currentPhotoUrl = authRepository.currentUser?.photoUrl?.toString()
                if (currentPhotoUrl != null && userProfile != null) {
                    userProfile = userProfile.copy(profilePictureUrl = currentPhotoUrl)
                }

                if (userProfile != null && reviews != null) {
                    _uiState.update {
                        it.copy(
                            userProfile = userProfile,
                            reviews = reviews,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } else {
                val error = userResult.exceptionOrNull() ?: reviewsResult.exceptionOrNull()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error?.localizedMessage
                    )
                }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)
            if (result.isSuccess) {
                _uiState.update { state ->
                    val newReviews = mutableListOf<com.example.gamercornerapp.data.ReviewItem>()
                    for (review in state.reviews) {
                        if (review.id != reviewId) {
                            newReviews.add(review)
                        }
                    }
                    state.copy(reviews = newReviews)
                }
            } else {
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
            _uiState.update { it.copy(isLoadingImage = true, errorMessage = null) }
            val uploadResult = storageRepository.uploadProfileImage(uri)
            if (uploadResult.isSuccess) {
                val downloadUrl = uploadResult.getOrNull()!!
                authRepository.updateProfilePicture(downloadUrl)

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
