
package com.example.gamercornerapp.ui.Screens.reviewMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.MapReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewMapViewModel @Inject constructor(
    private val repository: MapReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ReviewMapUiState()
    )

    val uiState: StateFlow<ReviewMapUiState> =
        _uiState.asStateFlow()

    private var reviewsJob: Job? = null

    companion object {
        private const val LAST_24_HOURS =
            24L * 60 * 60 * 1000

        private const val REFRESH_INTERVAL =
            60L * 1000
    }

    init {
        loadReviews()

        viewModelScope.launch {
            while (isActive) {
                delay(REFRESH_INTERVAL)
                loadReviews()
            }
        }
    }

    fun loadReviews() {
        reviewsJob?.cancel()

        reviewsJob = viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val sinceMillis =
                System.currentTimeMillis() - LAST_24_HOURS

            repository.getRecentReviews(sinceMillis)
                .catch { error ->

                    if (error is CancellationException) {
                        throw error
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                "No se pudieron cargar las reseñas"
                        )
                    }
                }
                .collect { reviews ->

                    _uiState.update { currentState ->

                        val selectedId =
                            currentState.selectedReviewId

                        val validSelection =
                            reviews.any {
                                it.id == selectedId
                            }

                        currentState.copy(
                            reviews = reviews,
                            selectedReviewId =
                                if (validSelection) {
                                    selectedId
                                } else {
                                    null
                                },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun selectReview(reviewId: String) {
        _uiState.update { currentState ->

            val exists = currentState.reviews.any {
                it.id == reviewId
            }

            if (exists) {
                currentState.copy(
                    selectedReviewId = reviewId
                )
            } else {
                currentState
            }
        }
    }

    fun clearSelection() {
        _uiState.update {
            it.copy(selectedReviewId = null)
        }
    }
}
