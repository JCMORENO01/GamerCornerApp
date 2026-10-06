package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.toFeedPost
import com.example.gamercornerapp.data.dto.toReviewItem
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource,
    private val gameRepository: GameRepository // Added GameRepository
) {

    suspend fun getAllReviewsAsFeed(): Result<List<FeedPost>> {
        return try {
            val dtos = remoteDataSource.getAllReviews()
            val feedPosts = dtos.map { reviewDto ->
                var gameDto = reviewDto.game
                if (gameDto == null) {
                    // Fetch the game details if they are missing
                    val gameResult = gameRepository.getGameById(reviewDto.gameId)
                    if (gameResult.isSuccess) {
                        // Create a dummy GameDTO from the fetched Game object. 
                        // It is better to use a mapper or just map it directly.
                        val game = gameResult.getOrNull()
                        if (game != null) {
                            gameDto = com.example.gamercornerapp.data.dto.GameDTO(
                                id = game.id.toString(),
                                title = game.title,
                                developer = game.developer,
                                year = game.year,
                                image = game.image.toString(), 
                                description = game.description,
                                tags = game.tags ?: emptyList(),
                                rating = game.rating,
                                reviewsCount = game.reviewsCount
                            )
                        }
                    }
                }
                
                // create a copy of the DTO with the game populated
                val populatedDto = reviewDto.copy(game = gameDto)
                populatedDto.toFeedPost()
            }
            Result.success(feedPosts)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun getReviewsByGame(gameId: String): Result<List<ReviewItem>> {
        return try {
            val dtos = remoteDataSource.getReviewsByGame(gameId)
            val reviews = dtos.map { it.toReviewItem() }
            Result.success(reviews)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun getReviewsByUser(userId: String): Result<List<ReviewItem>> {
        return try {
            val dtos = remoteDataSource.getReviewsByUser(userId)
            val reviews = dtos.map { it.toReviewItem() }
            Result.success(reviews)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun createReview(
        description: String,
        rating: Float,
        tags: List<String>?,
        userId: String = "1",
        gameId: String
    ): Result<Unit> {
        return try {
            val dto = CreateReviewDTO(
                description = description,
                rating = rating,
                tags = tags,
                userId = userId.toIntOrNull() ?: 1,
                gameId = gameId.toIntOrNull() ?: 1
            )
            remoteDataSource.createReview(dto)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun updateReview(
        reviewId: String,
        description: String,
        rating: Float,
        tags: List<String>?,
        userId: String = "1",
        gameId: String
    ): Result<Unit> {
        return try {
            val dto = CreateReviewDTO(
                description = description,
                rating = rating,
                tags = tags,
                userId = userId.toIntOrNull() ?: 1,
                gameId = gameId.toIntOrNull() ?: 1
            )
            remoteDataSource.updateReview(reviewId, dto)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            remoteDataSource.deleteReview(reviewId)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }
}
