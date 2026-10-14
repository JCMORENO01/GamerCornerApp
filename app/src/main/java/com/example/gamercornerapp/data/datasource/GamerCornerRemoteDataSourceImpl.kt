
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import javax.inject.Inject

class GamerCornerRemoteDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : GamerCornerRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> {
        return service.getGames()
    }

    override suspend fun getGameById(
        gameId: String
    ): GameDTO {
        return service.getGameById(gameId)
    }

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

    override suspend fun getUserById(
        userId: String
    ): UserProfileGenericDTO {
        return service.getUserById(userId)
    }

    override suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    ) {
        throw UnsupportedOperationException(
            "El registro de perfiles no está implementado en Retrofit"
        )
    }

    override suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO> {
        throw UnsupportedOperationException(
            "La búsqueda de usuarios no está implementada en Retrofit"
        )
    }

    override suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        throw UnsupportedOperationException(
            "La actualización de perfiles no está implementada en Retrofit"
        )
    }
}
