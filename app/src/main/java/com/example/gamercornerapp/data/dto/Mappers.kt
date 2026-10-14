
package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats

// Función para mapear videojuegos
fun GameDTO.toGame(): Game {
    val imgRes = when (id) {
        "1" -> R.drawable.elden
        "2" -> R.drawable.godofwar
        "3" -> R.drawable.hog
        "4" -> R.drawable.bal
        "5" -> R.drawable.cyberpunk
        else -> R.drawable.elden
    }

    return Game(
        id = id,
        title = title,
        developer = developer,
        year = year,
        image = imgRes,
        imageUrl = image?.takeIf {
            it.startsWith("http")
        },
        rating = rating ?: 0.0,
        reviewsCount = reviewsCount ?: 0,
        tags = tags ?: emptyList(),
        description = description ?: "",
        ratingDistribution = emptyList()
    )
}

// Función para mapear reseñas a ReviewItem
fun ReviewDTO.toReviewItem(): ReviewItem {
    val gameImg = when (gameId) {
        "1" -> R.drawable.mini_elden
        "2" -> R.drawable.godofwar
        "3" -> R.drawable.hog
        "4" -> R.drawable.bal
        "5" -> R.drawable.cyberpunk
        else -> R.drawable.mini_elden
    }

    return ReviewItem(
        id = id,
        gameTitle = game?.title ?: "Juego",
        rating = rating.toInt(),
        relativeDate = createdAt,
        gameImageId = gameImg,
        description = description,
        tags = tags,
        authorId = userId,
        authorName = user?.nickName
            ?.let { "@$it" }
            ?: "@Usuario",
        authorImageId = R.drawable.messi1,
        gameId = gameId,
        gameImageUrl = game?.image?.takeIf {
            it.startsWith("http")
        },
        authorImageUrl = user?.profilePictureUrl
            ?.takeIf { it.startsWith("http") },

        likesCount = likesCount
            .coerceIn(0L, Int.MAX_VALUE.toLong())
            .toInt(),

        isLiked = isLiked
    )
}

// Función para mapear reseñas al Feed
fun ReviewDTO.toFeedPost(): FeedPost {
    val authorProfile = UserProfile(
        id = userId,
        username = user?.username ?: "Usuario",
        nickName = user?.nickName ?: "usuario",
        bio = "No hay biografía",
        birthDate = null,
        profileBackgroundId = R.drawable.background_maquinitas,
        profileBgDescription = "No hay ubicación",
        profileImageId = R.drawable.messi1,
        profilePictureUrl = user?.profilePictureUrl,
        stats = UserStats(
            reviewsCount = 0,
            followersCount = 0,
            followingCount = 0
        )
    )

    val gameImg = when (gameId) {
        "1" -> R.drawable.elden
        "2" -> R.drawable.godofwar
        "3" -> R.drawable.hog
        "4" -> R.drawable.bal
        "5" -> R.drawable.cyberpunk
        else -> R.drawable.elden
    }

    val gameModel = Game(
        id = gameId,
        title = game?.title ?: "Juego",
        developer = "",
        year = 0,
        image = gameImg,
        imageUrl = game?.image?.takeIf {
            it.startsWith("http")
        }
    )

    return FeedPost(
        id = id,
        author = authorProfile,
        relativeTime = createdAt,
        game = gameModel,
        rating = rating.toDouble(),
        description = description,
        tags = tags,

        likesCount = likesCount
            .coerceIn(0L, Int.MAX_VALUE.toLong())
            .toInt(),

        isLiked = isLiked,

        commentsCount = 0
    )
}
