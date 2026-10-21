
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import kotlinx.coroutines.flow.Flow

interface ReviewRemoteDataSource {

    suspend fun getAllReviews(): List<ReviewDTO>

    suspend fun getReviewsByGame(
        gameId: String
    ): List<ReviewDTO>

    suspend fun getReviewsByUser(
        userId: String
    ): List<ReviewDTO>

    suspend fun createReview(
        review: CreateReviewDTO
    )

    suspend fun updateReview(
        reviewId: String,
        review: CreateReviewDTO
    )

    suspend fun deleteReview(
        reviewId: String
    )

    suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    )

    fun listenAllReviews(): Flow<List<ReviewDTO>>

    fun listenReviewsByGame(
        gameId: String
    ): Flow<List<ReviewDTO>>

    fun listenReviewsByUser(
        userId: String
    ): Flow<List<ReviewDTO>>
}
