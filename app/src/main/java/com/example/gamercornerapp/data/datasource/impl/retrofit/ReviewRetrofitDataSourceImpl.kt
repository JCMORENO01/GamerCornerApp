
package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : ReviewRemoteDataSource {

    override suspend fun getAllReviews(): List<ReviewDTO> {
        return service.getAllReviews()
    }

    override suspend fun getReviewsByGame(
        gameId: String
    ): List<ReviewDTO> {
        return service.getReviewsByGame(gameId)
    }

    override suspend fun getReviewsByUser(
        userId: String
    ): List<ReviewDTO> {
        return service.getReviewsByUser(userId)
    }

    override suspend fun createReview(
        review: CreateReviewDTO
    ) {
        service.createReview(review)
    }

    override suspend fun updateReview(
        reviewId: String,
        review: CreateReviewDTO
    ) {
        service.updateReview(reviewId, review)
    }

    override suspend fun deleteReview(
        reviewId: String
    ) {
        service.deleteReview(reviewId)
    }

    override suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    ) {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarado un endpoint de likes"
        )
    }

    override fun listenAllReviews(): Flow<List<ReviewDTO>> {
        throw UnsupportedOperationException(
            "La implementación Retrofit todavía no tiene tiempo real"
        )
    }
}
