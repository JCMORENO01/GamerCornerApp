package com.example.gamercornerapp.data.datasource.services

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.HTTP

interface GamerCornerService {

    @GET("api/games")
    suspend fun getGamesRaw(): List<JsonObject>

    @GET("api/games/{id}")
    suspend fun getGameByIdRaw(
        @Path("id") id: String
    ): JsonObject

    @GET("api/reviews")
    suspend fun getAllReviewsRaw(): List<JsonObject>

    @GET("api/reviews/game/{gameId}")
    suspend fun getReviewsByGameRaw(
        @Path("gameId") gameId: String
    ): List<JsonObject>

    @GET("api/reviews/user/{userId}")
    suspend fun getReviewsByUserRaw(
        @Path("userId") userId: String
    ): List<JsonObject>

    @POST("api/reviews")
    suspend fun createReviewRaw(
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): JsonObject

    @PUT("api/reviews/{id}")
    suspend fun updateReviewRaw(
        @Path("id") id: String,
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): JsonObject

    @DELETE("api/reviews/{id}")
    suspend fun deleteReviewRaw(
        @Path("id") id: String
    ): JsonObject

    @GET("api/reviews/{id}/likes/{userId}")
    suspend fun getReviewLikeStatusRaw(
        @Path("id") id: String,
        @Path("userId") userId: String
    ): JsonObject

    @POST("api/reviews/{id}/likes")
    suspend fun likeReviewRaw(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): JsonObject

    @HTTP(
        method = "DELETE",
        path = "api/reviews/{id}/likes",
        hasBody = true
    )
    suspend fun unlikeReviewRaw(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): JsonObject

    @GET("api/users/{id}")
    suspend fun getUserByIdRaw(
        @Path("id") id: String
    ): JsonObject

    @POST("api/users")
    suspend fun registerUserRaw(
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): JsonObject

    @PUT("api/users/{id}")
    suspend fun updateUserRaw(
        @Path("id") id: String,
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): JsonObject

    @GET("api/users/search")
    suspend fun searchUsersRaw(
        @Query("q") query: String
    ): List<JsonObject>

    @PATCH("api/users/{id}/fcm-token")
    suspend fun updateFcmTokenRaw(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): JsonObject

    @GET("api/users/{id}/is-following")
    suspend fun isFollowingRaw(
        @Path("id") id: String,
        @Query("currentUserId") currentUserId: String
    ): JsonObject

    @POST("api/users/{id}/follow")
    suspend fun followUserRaw(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): JsonObject

    @HTTP(
        method = "DELETE",
        path = "api/users/{id}/follow",
        hasBody = true
    )
    suspend fun unfollowUserRaw(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): JsonObject

    @GET("api/users/{id}/followers")
    suspend fun getFollowersRaw(
        @Path("id") id: String
    ): List<JsonObject>

    @GET("api/users/{id}/following")
    suspend fun getFollowingRaw(
        @Path("id") id: String
    ): List<JsonObject>
}