
package com.example.gamercornerapp.ui.Screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedState())

    val uiState: StateFlow<FeedState> =
        _uiState.asStateFlow()

    private var feedJob: Job? = null
    private var followingJob: Job? = null

    private var allPosts: List<FeedPost> = emptyList()

    private var followingIds: Set<String> = emptySet()

    private val pendingLikes = mutableSetOf<String>()

    // Función para escuchar las reseñas en tiempo real
    fun loadFeed() {
        feedJob?.cancel()

        feedJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            reviewRepository.listenAllReviewsAsFeed()
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage
                                ?: "No se pudo cargar el Feed"
                        )
                    }
                }
                .collect { posts ->
                    allPosts = posts
                    updateVisiblePosts()
                }
        }
    }

    // Función para filtrar las publicaciones
    private fun updateVisiblePosts() {
        val state = _uiState.value

        val visiblePosts = if (state.selectedTabIndex == 0) {
            allPosts
        } else {
            allPosts.filter { post ->
                post.author.id in followingIds
            }
        }

        _uiState.update {
            it.copy(
                posts = visiblePosts,
                isLoading = false
            )
        }
    }

    // Función para obtener los usuarios seguidos
    private fun loadFollowing() {
        followingJob?.cancel()

        followingJob = viewModelScope.launch {
            val userId = authRepository.currentUser?.uid

            if (userId == null) {
                followingIds = emptySet()

                _uiState.update {
                    it.copy(
                        posts = emptyList(),
                        isLoading = false,
                        errorMessage = "Debes iniciar sesión"
                    )
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val result = userRepository.getFollowing(userId)

                if (result.isSuccess) {
                    followingIds = result.getOrThrow()
                        .map { user -> user.id }
                        .toSet()

                    if (_uiState.value.selectedTabIndex == 1) {
                        updateVisiblePosts()
                    }
                } else {
                    followingIds = emptySet()

                    _uiState.update {
                        it.copy(
                            posts = emptyList(),
                            isLoading = false,
                            errorMessage = result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudieron cargar los seguidos"
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                followingIds = emptySet()

                _uiState.update {
                    it.copy(
                        posts = emptyList(),
                        isLoading = false,
                        errorMessage = e.localizedMessage
                            ?: "No se pudieron cargar los seguidos"
                    )
                }
            }
        }
    }

    // Función para cambiar las pestañas del Feed
    fun onTabSelected(index: Int) {
        if (index !in 0..1) return

        _uiState.update {
            it.copy(
                selectedTabIndex = index,
                errorMessage = null
            )
        }

        if (index == 0) {
            followingJob?.cancel()
            updateVisiblePosts()
        } else {
            loadFollowing()
        }
    }

    // Función para dar o quitar like
    fun toggleReviewLike(reviewId: String) {
        if (reviewId.isBlank()) return

        val userId = authRepository.currentUser?.uid

        if (userId == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "Debes iniciar sesión para dar like"
                )
            }
            return
        }

        if (!pendingLikes.add(reviewId)) return

        // Actualización optimista de la UI
        allPosts = allPosts.map { post ->
            if (post.id == reviewId) {
                val newIsLiked = !post.isLiked
                val newCount = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                post.copy(isLiked = newIsLiked, likesCount = newCount)
            } else {
                post
            }
        }
        updateVisiblePosts()

        viewModelScope.launch {
            try {
                val result = reviewRepository.toggleReviewLike(
                    reviewId = reviewId,
                    userId = userId
                )

                if (result.isFailure) {
                    // Revertir cambio optimista en caso de error
                    allPosts = allPosts.map { post ->
                        if (post.id == reviewId) {
                            val revertedIsLiked = !post.isLiked
                            val revertedCount = if (revertedIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                            post.copy(isLiked = revertedIsLiked, likesCount = revertedCount)
                        } else {
                            post
                        }
                    }
                    updateVisiblePosts()

                    _uiState.update {
                        it.copy(
                            errorMessage = result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudo actualizar el like"
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage
                            ?: "No se pudo actualizar el like"
                    )
                }
            } finally {
                pendingLikes.remove(reviewId)
            }
        }
    }

    // Función para limpiar los errores
    fun onErrorDismiss() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
