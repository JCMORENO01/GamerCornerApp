
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO

interface GamerCornerRemoteDataSource {

    suspend fun getGames(): List<GameDTO>

    suspend fun getGameById(gameId: String): GameDTO

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

    suspend fun getUserById(
        userId: String
    ): UserProfileGenericDTO

    suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    )

    suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO>

    suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    )
}
