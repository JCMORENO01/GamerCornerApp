package com.example.gamercornerapp.ui.Screens.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileState())
    val uiState: StateFlow<UserProfileState> = _uiState.asStateFlow()

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userResult = userRepository.getUserById(userId)
            val reviewsResult = reviewRepository.getReviewsByUser(userId)

            if (userResult.isSuccess && reviewsResult.isSuccess) {
                val userProfile = userResult.getOrNull()
                val reviews = reviewsResult.getOrNull()

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
}
