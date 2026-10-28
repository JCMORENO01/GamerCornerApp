package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : ReviewRemoteDataSource {

    override suspend fun getAllReviews(): List<ReviewDTO> =
        service.getAllReviewsRaw().map { it.toReviewDTO() }

    override suspend fun getReviewsByGame(gameId: String): List<ReviewDTO> =
        service.getReviewsByGameRaw(gameId).map { it.toReviewDTO() }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDTO> =
        service.getReviewsByUserRaw(userId).map { it.toReviewDTO() }

    override suspend fun createReview(review: CreateReviewDTO) {
        service.createReviewRaw(review.toBackendBody())
    }

    override suspend fun updateReview(
        reviewId: String,
        review: CreateReviewDTO
    ) {
        service.updateReviewRaw(reviewId, review.toBackendBody())
    }

    override suspend fun deleteReview(reviewId: String) {
        service.deleteReviewRaw(reviewId)
    }

    override suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    ) {
        val status = service.getReviewLikeStatusRaw(reviewId, userId)
        val body = mapOf("userId" to userId)

        if (status.bool("hasLiked")) {
            service.unlikeReviewRaw(reviewId, body)
        } else {
            service.likeReviewRaw(reviewId, body)
        }
    }

    // Retrofit HTTP no proporciona listeners push.
    // Estos Flows emiten una consulta inicial.
    override fun listenAllReviews(): Flow<List<ReviewDTO>> =
        flow {
            emit(getAllReviews())
        }

    override fun listenReviewsByGame(
        gameId: String
    ): Flow<List<ReviewDTO>> =
        flow {
            emit(getReviewsByGame(gameId))
        }

    override fun listenReviewsByUser(
        userId: String
    ): Flow<List<ReviewDTO>> =
        flow {
            emit(getReviewsByUser(userId))
        }

    override suspend fun toggleSaveReview(
        reviewId: String,
        userId: String
    ) {
    }

    override suspend fun getSavedReviewsByUser(
        userId: String
    ): List<ReviewDTO> {
        return emptyList()
    }
}