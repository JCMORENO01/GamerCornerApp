package com.example.gamercornerapp.ui.Screens.selfProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.local.LocalDataProvider
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
            _uiState.update { it.copy(isLoadingImage = true, errorMessage = null) }

            val userResult = userRepository.getUserById("1")
            val reviewsResult = reviewRepository.getReviewsByUser("1")

            val currentPhotoUrl = authRepository.currentUser?.photoUrl?.toString()
            val userProfile = userResult.getOrElse { LocalDataProvider.userProfile }
            val reviews = reviewsResult.getOrElse { LocalDataProvider.reviews }

            val finalProfile = userProfile.copy(
                profilePictureUrl = currentPhotoUrl ?: userProfile.profilePictureUrl
            )

            _uiState.update {
                it.copy(
                    userProfile = finalProfile,
                    reviews = reviews,
                    isLoadingImage = false
                )
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            reviewRepository.deleteReview(reviewId).onSuccess {
                // Filter deleted review out
                _uiState.update { state ->
                    state.copy(reviews = state.reviews.filterNot { it.id == reviewId })
                }
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
            val result = storageRepository.uploadProfileImage(uri)

            result.onSuccess { downloadUrl ->
                authRepository.updateProfilePicture(downloadUrl)

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoadingImage = false,
                        userProfile = currentState.userProfile?.copy(
                            profilePictureUrl = downloadUrl
                        )
                    )
                }
            }.onFailure { error ->
                val errorMsg = when {
                    error.message?.contains("unauthenticated", ignoreCase = true) == true ->
                        "Error: Usuario no autenticado. Inicia sesión nuevamente."
                    error.message?.contains("quota", ignoreCase = true) == true || error.message?.contains("plan", ignoreCase = true) == true ->
                        "Error de almacenamiento: Se requiere configurar el plan de Firebase Storage."
                    else ->
                        "Error al subir imagen: ${error.localizedMessage ?: "Verifica tu conexión e intenta de nuevo."}"
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
