package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO

interface GamerCornerRemoteDataSource {
    suspend fun getGames(): List<GameDTO>
    suspend fun getGameById(gameId: String): GameDTO
    suspend fun getAllReviews(): List<ReviewDTO>
    suspend fun getReviewsByGame(gameId: String): List<ReviewDTO>
    suspend fun getReviewsByUser(userId: String): List<ReviewDTO>
    suspend fun createReview(review: CreateReviewDTO): ReviewDTO
    suspend fun updateReview(reviewId: String, review: CreateReviewDTO): ReviewDTO
    suspend fun deleteReview(reviewId: String): Any
    suspend fun getUserById(userId: String): UserProfileDTO
}
