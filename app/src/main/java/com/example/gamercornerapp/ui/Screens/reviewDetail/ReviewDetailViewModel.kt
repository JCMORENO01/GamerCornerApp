package com.example.gamercornerapp.ui.Screens.reviewDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    init {
        if (reviewId.isNotBlank()) {
            loadReviewDetails()
            loadComments()
        }
    }



    private fun loadReviewDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = reviewRepository.getAllReviewsAsFeed()
            if (result.isSuccess) {
                val postFound = result.getOrNull()?.find { it.id == reviewId }
                _uiState.update { it.copy(post = postFound, isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar la reseña"
                    )
                }
            }
        }
    }



    private fun loadComments() {
        viewModelScope.launch {
            reviewRepository.listenCommentsForReview(reviewId).collect { commentsList ->
                _uiState.update { it.copy(comments = commentsList) }
            }
        }
    }

    fun onCommentInputChanged(text: String) {
        _uiState.update { it.copy(commentInput = text) }
    }

    fun postReviewComment() {
        val currentText = _uiState.value.commentInput.trim()
        if (currentText.isEmpty()) return

        val currentUserUid = authRepository.currentUser?.uid
        if (currentUserUid.isNullOrBlank()) {
            _uiState.update { it.copy(errorMessage = "Usuario no autenticado") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingComment = true, errorMessage = null) }

            val result = reviewRepository.addCommentToReview(
                reviewId = reviewId,
                text = currentText,
                userId = currentUserUid
            )

            if (result.isSuccess) {
                _uiState.update { state ->
                    val updatedPost = state.post?.copy(
                        commentsCount = state.post.commentsCount + 1
                    )
                    state.copy(
                        post = updatedPost,
                        commentInput = "",
                        isLoadingComment = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoadingComment = false,
                        errorMessage = result.exceptionOrNull()?.localizedMessage
                            ?: "No se pudo enviar el comentario"
                    )
                }
            }
        }
    }
}