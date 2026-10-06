package com.example.gamercornerapp.data.datasource.services

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GamerCornerService {

    @GET("api/games")
    suspend fun getGames(): List<GameDTO>

    @GET("api/games/{id}")
    suspend fun getGameById(
        @Path("id") gameId: String
    ): GameDTO

    @GET("api/reviews")
    suspend fun getAllReviews(): List<ReviewDTO>

    @GET("api/reviews/game/{gameId}")
    suspend fun getReviewsByGame(
        @Path("gameId") gameId: String
    ): List<ReviewDTO>

    @POST("api/reviews")
    suspend fun createReview(
        @Body review: CreateReviewDTO
    )

    @PUT("api/reviews/{id}")
    suspend fun updateReview(
        @Path("id") reviewId: String,
        @Body review: CreateReviewDTO
    )

    @DELETE("api/reviews/{id}")
    suspend fun deleteReview(
        @Path("id") reviewId: String
    )

    @GET("api/users/{id}")
    suspend fun getUserById(
        @Path("id") userId: String
    ): UserProfileDTO

    @GET("api/reviews/user/{userId}")
    suspend fun getReviewsByUser(
        @Path("userId") userId: String
    ): List<ReviewDTO>
}
