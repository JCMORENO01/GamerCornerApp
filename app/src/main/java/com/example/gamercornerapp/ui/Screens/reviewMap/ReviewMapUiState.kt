package com.example.gamercornerapp.ui.Screens.reviewMap

import com.example.gamercornerapp.data.MapReview

data class ReviewMapUiState(
    val reviews: List<MapReview> = emptyList(),
    val selectedReviewId: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val selectedReview: MapReview?
        get() = reviews.find {
            it.id == selectedReviewId
        }

    val selectedReviewsAtSameLocation: List<MapReview>
        get() {
            val selected = selectedReview ?: return emptyList()
            return reviews.filter { 
                it.latitude == selected.latitude && 
                it.longitude == selected.longitude 
            }
        }
}
