
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.CreateReviewGameDTO
import com.example.gamercornerapp.data.dto.CreateReviewUserDTO
import com.example.gamercornerapp.data.dto.toFeedPost
import com.example.gamercornerapp.data.dto.toReviewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val remoteDataSource: ReviewRemoteDataSource,
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun getAllReviewsAsFeed(): Result<List<FeedPost>> {
        return try {
            val reviews = remoteDataSource.getAllReviews()

            Result.success(
                reviews.map { it.toFeedPost() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByGame(
        gameId: String
    ): Result<List<ReviewItem>> {
        return try {
            val reviews = remoteDataSource.getReviewsByGame(gameId)

            Result.success(
                reviews.map { it.toReviewItem() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByUser(
        userId: String
    ): Result<List<ReviewItem>> {
        return try {
            val reviews = remoteDataSource.getReviewsByUser(userId)

            Result.success(
                reviews.map { it.toReviewItem() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun listenAllReviewsAsFeed(): Flow<List<FeedPost>> {
        return remoteDataSource.listenAllReviews()
            .map { reviews ->
                reviews.map { review ->
                    review.toFeedPost()
                }
            }
    }

    fun listenReviewsByGame(gameId: String): Flow<List<ReviewItem>> {
        return remoteDataSource.listenReviewsByGame(gameId)
            .map { reviews ->
                reviews.map { review ->
                    review.toReviewItem()
                }
            }
    }

    fun listenReviewsByUser(userId: String): Flow<List<ReviewItem>> {
        return remoteDataSource.listenReviewsByUser(userId)
            .map { reviews ->
                reviews.map { review ->
                    review.toReviewItem()
                }
            }
    }

    suspend fun getFollowingReviewsAsFeed(
        userId: String
    ): Result<List<FeedPost>> {
        return try {
            val followingIds = userRepository
                .getFollowing(userId)
                .getOrThrow()
                .map { it.id }
                .toSet()

            val reviews = remoteDataSource.getAllReviews()
                .filter { it.userId in followingIds }

            Result.success(
                reviews.map { it.toFeedPost() }
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
                "No se pudo obtener el usuario"
            }

            require(authRepository.currentUser?.uid == userId) {
                "Usuario no autenticado"
            }

            val user = userRepository.getUserById(userId)
                .getOrThrow()

            val game = gameRepository.getGameById(gameId)
                .getOrThrow()

            val authPhotoUrl = authRepository.currentUser
                ?.photoUrl
                ?.toString()

            val profilePicUrl = user.profilePictureUrl
                ?.takeIf { it.isNotBlank() }
                ?: authPhotoUrl

            val reviewUser = CreateReviewUserDTO(
                username = user.username,
                nickName = user.nickName,
                profilePictureUrl = profilePicUrl
            )

            val reviewGame = CreateReviewGameDTO(
                title = game.title,
                image = game.imageUrl
            )

            val createdAt = if (reviewId == null) {
                SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.US
                ).format(Date())
            } else {
                null
            }

            val review = CreateReviewDTO(
                description = description,
                rating = rating,
                tags = tags,
                userId = userId,
                gameId = gameId,
                createdAt = createdAt,
                user = reviewUser,
                game = reviewGame
            )

            if (reviewId == null) {
                remoteDataSource.createReview(review)
            } else {
                remoteDataSource.updateReview(
                    reviewId,
                    review
                )
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

    suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    ): Result<Unit> {
        return try {
            require(reviewId.isNotBlank())
            require(userId.isNotBlank())

            require(authRepository.currentUser?.uid == userId) {
                "Usuario no autenticado"
            }

            remoteDataSource.toggleReviewLike(
                reviewId,
                userId
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun toggleSaveReview(
        reviewId: String,
        userId: String
    ): Result<Unit> {
        return try {
            require(reviewId.isNotBlank())
            require(userId.isNotBlank())

            require(authRepository.currentUser?.uid == userId) {
                "Usuario no autenticado"
            }

            remoteDataSource.toggleSaveReview(
                reviewId,
                userId
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getSavedReviewsByUser(
        userId: String
    ): Result<List<ReviewItem>> {
        return try {
            val reviews = remoteDataSource.getSavedReviewsByUser(userId)
            Result.success(reviews.map { it.toReviewItem() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
