package com.example.gamercornerapp.ui.Screens.reviewDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
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
class ReviewDetailViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val reviewId: String = savedStateHandle.get<String>("reviewId") ?: ""

    private val _uiState = MutableStateFlow(ReviewDetailState(isLoading = true))
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    private var reviewJob: Job? = null

    init {
        loadPost()
    }

    fun loadPost() {
        reviewJob?.cancel()
        reviewJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            reviewRepository.listenAllReviewsAsFeed()
                .catch {
                    val fallbackPost = LocalDataProvider.posts.find { p -> p.id == reviewId }
                        ?: LocalDataProvider.posts.firstOrNull()
                    _uiState.update { state ->
                        state.copy(
                            post = fallbackPost,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .collect { posts ->
                    val allPosts = if (posts.isNotEmpty()) posts else LocalDataProvider.posts
                    val foundPost = allPosts.find { p -> p.id == reviewId }
                        ?: LocalDataProvider.posts.find { p -> p.id == reviewId }
                        ?: LocalDataProvider.posts.firstOrNull()

                    _uiState.update { state ->
                        state.copy(
                            post = foundPost,
                            isLoading = false,
                            errorMessage = if (foundPost == null) "No se encontró la reseña" else null
                        )
                    }
                }
        }
    }

    fun toggleReviewLike() {
        val currentPost = _uiState.value.post ?: return
        val userId = authRepository.currentUser?.uid ?: "local_user"

        val newIsLiked = !currentPost.isLiked
        val newCount = if (newIsLiked) currentPost.likesCount + 1 else (currentPost.likesCount - 1).coerceAtLeast(0)
        val updatedPost = currentPost.copy(isLiked = newIsLiked, likesCount = newCount)

        _uiState.update {
            it.copy(post = updatedPost)
        }

        viewModelScope.launch {
            if (authRepository.currentUser != null) {
                try {
                    val result = reviewRepository.toggleReviewLike(currentPost.id, userId)
                    if (result.isFailure) {
                        _uiState.update { it.copy(post = currentPost) }
                    }
                } catch (_: Exception) {
                    _uiState.update { it.copy(post = currentPost) }
                }
            }
        }
    }

    fun toggleSaveReview() {
        val currentPost = _uiState.value.post ?: return
        val userId = authRepository.currentUser?.uid ?: "local_user"

        val updatedPost = currentPost.copy(isBookmarked = !currentPost.isBookmarked)

        _uiState.update {
            it.copy(post = updatedPost)
        }

        viewModelScope.launch {
            if (authRepository.currentUser != null) {
                try {
                    reviewRepository.toggleSaveReview(currentPost.id, userId)
                } catch (_: Exception) {
                    _uiState.update { it.copy(post = currentPost) }
                }
            }
        }
    }
}
