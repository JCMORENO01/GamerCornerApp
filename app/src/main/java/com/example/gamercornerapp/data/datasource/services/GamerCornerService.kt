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

    // ==========================================
    // 1. HOME & ARTÍCULOS (GAMES)
    // ==========================================

    @GET("games")
    suspend fun getGames(): List<GameDTO>

    @GET("games/{id}")
    suspend fun getGameById(
        @Path("id") gameId: String
    ): GameDTO

    // ==========================================
    // 2. REVIEWS (COMENTARIOS)
    // ==========================================

    @GET("reviews")
    suspend fun getAllReviews(): List<ReviewDTO>

    @GET("reviews/game/{gameId}")
    suspend fun getReviewsByGame(
        @Path("gameId") gameId: String
    ): List<ReviewDTO>

    @POST("reviews")
    suspend fun createReview(
        @Body review: CreateReviewDTO
    ): ReviewDTO

    @PUT("reviews/{id}")
    suspend fun updateReview(
        @Path("id") reviewId: String,
        @Body review: CreateReviewDTO
    ): ReviewDTO

    @DELETE("reviews/{id}")
    suspend fun deleteReview(
        @Path("id") reviewId: String
    ): Any

    // ==========================================
    // 3. PERFIL DE USUARIO
    // ==========================================

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") userId: String
    ): UserProfileDTO

    @GET("reviews/user/{userId}")
    suspend fun getReviewsByUser(
        @Path("userId") userId: String
    ): List<ReviewDTO>
}
