
package com.example.gamercornerapp.ui.Screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedState())

    val uiState: StateFlow<FeedState> =
        _uiState.asStateFlow()

    fun loadFeed() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }



            val result = reviewRepository.getAllReviewsAsFeed()

            android.util.Log.d(
                "GAMERCORNER_FEED",
                "Resultado exitoso: ${result.isSuccess}"
            )

            android.util.Log.d(
                "GAMERCORNER_FEED",
                "Reseñas obtenidas: ${result.getOrNull()?.size}"
            )

            android.util.Log.d(
                "GAMERCORNER_FEED",
                "Error: ${result.exceptionOrNull()?.message}"
            )


            if (result.isSuccess) {

                val feedPosts = result.getOrNull()
                    ?: emptyList()

                _uiState.update {
                    it.copy(
                        posts = feedPosts,
                        isLoading = false,
                        errorMessage = null
                    )
                }

            } else {

                _uiState.update {
                    it.copy(
                        posts = emptyList(),
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()
                            ?.localizedMessage
                            ?: "No se pudieron cargar las reseñas"
                    )
                }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(selectedTabIndex = index)
        }
    }
}
