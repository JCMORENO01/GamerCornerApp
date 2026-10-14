
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.CreateReviewUserDTO
import com.example.gamercornerapp.data.dto.CreateReviewGameDTO
import com.example.gamercornerapp.data.dto.toFeedPost
import com.example.gamercornerapp.data.dto.toReviewItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource,
    private val gameRepository: GameRepository,
    private val authRepository: AuthRepository
) {

    suspend fun getAllReviewsAsFeed(): Result<List<FeedPost>> {
        return try {
            val dtos = remoteDataSource.getAllReviews()

            Result.success(
                dtos.map { it.toFeedPost() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByGame(
        gameId: String
    ): Result<List<ReviewItem>> {
        return try {
            val dtos = remoteDataSource.getReviewsByGame(gameId)

            Result.success(
                dtos.map { it.toReviewItem() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByUser(
        userId: String
    ): Result<List<ReviewItem>> {
        return try {
            val dtos = remoteDataSource.getReviewsByUser(userId)

            Result.success(
                dtos.map { it.toReviewItem() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createReview(
        description: String,
        rating: Float,
        tags: List<String>?,
        userId: String,
        gameId: String
    ): Result<Unit> {
        return saveReview(
            description = description,
            rating = rating,
            tags = tags,
            userId = userId,
            gameId = gameId
        )
    }

    suspend fun updateReview(
        reviewId: String,
        description: String,
        rating: Float,
        tags: List<String>?,
        userId: String,
        gameId: String
    ): Result<Unit> {
        return saveReview(
            description = description,
            rating = rating,
            tags = tags,
            userId = userId,
            gameId = gameId,
            reviewId = reviewId
        )
    }

    private suspend fun saveReview(
        description: String,
        rating: Float,
        tags: List<String>?,
        userId: String,
        gameId: String,
        reviewId: String? = null
    ): Result<Unit> {
        return try {

            require(userId.isNotBlank()) {
                "No se pudo obtener el usuario actual"
            }

            // Obtener los datos del usuario
            val userDto = remoteDataSource.getUserById(userId)
            val user = userDto.toUserProfile()

            // Obtener los datos del videojuego
            val game = gameRepository.getGameById(gameId)
                .getOrThrow()

            // Obtener la foto desde Authentication
            val currentUser = authRepository.currentUser

            val authPhotoUrl = if (currentUser?.uid == userId) {
                currentUser.photoUrl?.toString()
            } else {
                null
            }

            // Utilizar Firestore si Authentication no tiene foto
            val profileImageUrl = authPhotoUrl
                ?: user.profilePictureUrl

            // Desnormalización del usuario
            val reviewUser = CreateReviewUserDTO(
                username = user.username,
                nickName = user.nickName,
                profilePictureUrl = profileImageUrl
            )

            // Desnormalización del videojuego
            val reviewGame = CreateReviewGameDTO(
                title = game.title,
                image = game.imageUrl
            )

            // Fecha de publicación
            val fechaPublicacion = if (reviewId == null) {
                SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.US
                ).format(Date())
            } else {
                null
            }

            // Construcción del DTO
            val dto = CreateReviewDTO(
                description = description,
                rating = rating,
                tags = tags,
                userId = userId,
                gameId = gameId,
                createdAt = fechaPublicacion,
                user = reviewUser,
                game = reviewGame
            )

            // Crear o actualizar la reseña
            if (reviewId != null) {
                remoteDataSource.updateReview(reviewId, dto)
            } else {
                remoteDataSource.createReview(dto)
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteReview(
        reviewId: String
    ): Result<Unit> {
        return try {
            remoteDataSource.deleteReview(reviewId)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
