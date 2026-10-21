package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.dto.*
import com.google.gson.JsonObject

internal fun JsonObject.str(key: String): String =
    get(key)?.takeUnless { it.isJsonNull }?.asString.orEmpty()

internal fun JsonObject.int(key: String): Int =
    get(key)?.takeUnless { it.isJsonNull }?.asInt ?: 0

internal fun JsonObject.long(key: String): Long =
    get(key)?.takeUnless { it.isJsonNull }?.asLong ?: 0L

internal fun JsonObject.double(key: String): Double =
    get(key)?.takeUnless { it.isJsonNull }?.asDouble ?: 0.0

internal fun JsonObject.bool(key: String): Boolean =
    get(key)?.takeUnless { it.isJsonNull }?.asBoolean ?: false

internal fun JsonObject.obj(key: String): JsonObject? =
    get(key)?.takeIf { it.isJsonObject }?.asJsonObject

internal fun JsonObject.strings(key: String): List<String> =
    get(key)?.takeIf { it.isJsonArray }
        ?.asJsonArray
        ?.map { it.asString }
        ?: emptyList()

internal fun JsonObject.toGameDTO() = GameDTO(
    id = str("id"),
    title = str("title"),
    developer = str("developer"),
    year = int("year"),
    image = str("image").ifBlank { null },
    description = str("description").ifBlank { null },
    tags = strings("tags"),
    rating = double("rating"),
    reviewsCount = int("reviewsCount")
)

internal fun JsonObject.toReviewDTO() = ReviewDTO(
    id = str("id"),
    description = str("description"),
    rating = double("rating").toFloat(),
    tags = strings("tags"),
    userId = str("userId"),
    gameId = str("gameId"),
    createdAt = str("createdAt"),
    likesCount = long("likesCount"),
    user = obj("user")?.let {
        CreateReviewUserDTO(
            username = it.str("username"),
            nickName = it.str("nickName"),
            profilePictureUrl =
                it.str("profilePictureUrl").ifBlank { null }
        )
    },
    game = obj("game")?.let {
        CreateReviewGameDTO(
            title = it.str("title"),
            image = it.str("image").ifBlank { null }
        )
    }
)

internal fun JsonObject.toUserProfileDTO() = UserProfileDTO(
    id = str("uid").ifBlank { str("id") },
    username = str("username"),
    nickName = str("nickName"),
    bio = str("bio").ifBlank { null },
    birthDate = str("birthDate").ifBlank { null },
    profileBackgroundId = int("profileBackgroundId"),
    profileBgDescription =
        str("profileBgDescription").ifBlank { null },
    profileImageId = int("profileImageId"),
    profilePictureUrl =
        str("profilePictureUrl").ifBlank { null },
    followersCount = long("followersCount"),
    followingCount = long("followingCount"),
    FCM_token = str("fcm_token").ifBlank { null }
)

internal fun CreateReviewDTO.toBackendBody(): Map<String, Any?> =
    mapOf(
        "description" to description,
        "rating" to rating,
        "tags" to tags,
        "userId" to userId,
        "gameId" to gameId
    )

internal fun UserProfileDTO.toBackendBody(
    firebaseUid: String
): Map<String, Any?> =
    mapOf(
        "uid" to firebaseUid,
        "username" to username,
        "nickName" to nickName,
        "bio" to bio,
        "birthDate" to birthDate,
        "profileBackgroundId" to profileBackgroundId,
        "profileBgDescription" to profileBgDescription,
        "profileImageId" to profileImageId,
        "profilePictureUrl" to profilePictureUrl,
        "fcm_token" to FCM_token
    )