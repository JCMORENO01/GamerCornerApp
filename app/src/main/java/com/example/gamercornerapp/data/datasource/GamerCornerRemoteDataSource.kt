
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import kotlinx.coroutines.flow.Flow

interface GamerCornerRemoteDataSource {

    // Videojuegos
    suspend fun getGames(): List<GameDTO>

    suspend fun getGameById(gameId: String): GameDTO

    // Reseñas
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

    suspend fun deleteReview(reviewId: String)

    // Likes
    suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    )


    // Tiempo real
    fun listenAllReviews(): Flow<List<ReviewDTO>>

    // Usuarios
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

    // Seguimiento
    suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    )

    suspend fun isFollowing(
        currentUserId: String,
        targetUserId: String
    ): Boolean

    suspend fun getFollowers(
        userId: String
    ): List<String>

    suspend fun getFollowing(
        userId: String
    ): List<String>

}
