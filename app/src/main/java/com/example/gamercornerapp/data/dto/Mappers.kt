package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats

fun GameDTO.toGame(): Game {
    val imgRes = if (image?.startsWith("http") == true) {
        R.drawable.elden // Fallback for now if it's a URL, since Game expects an Int resource
    } else {
        when (id) {
            "1" -> R.drawable.elden
            "2" -> R.drawable.godofwar
            "3" -> R.drawable.hog
            "4" -> R.drawable.bal
            "5" -> R.drawable.cyberpunk
            else -> R.drawable.elden
        }
    }
    return Game(
        id = id,
        title = title,
        developer = developer,
        year = year,
        image = imgRes,
        imageUrl = if (image?.startsWith("http") == true) image else null,
        rating = rating ?: 4.5,
        reviewsCount = reviewsCount ?: 10,
        tags = tags ?: emptyList(), // Asignamos lista vacía si es null
        description = description ?: "",
        ratingDistribution = listOf(
            GameRatingBar(5, 0.8f),
            GameRatingBar(4, 0.15f),
            GameRatingBar(3, 0.05f)
        )
    )
}

fun UserProfileDTO.toUserProfile(reviewsCount: Int = 0): UserProfile {
    val bgRes = R.drawable.background_maquinitas
    val imgRes = when (id) {
        "1" -> R.drawable.messi1
        "2" -> R.drawable.messi2
        else -> R.drawable.messi1
    }
    return UserProfile(
        id = id,
        username = username,
        nickName = nickName,
        bio = bio ?: "",
        profileBackgroundId = bgRes,
        profileBgDescription = profileBgDescription ?: "Background",
        profileImageId = imgRes,
        profilePictureUrl = profilePictureUrl,
        stats = UserStats(
            reviewsCount = reviewsCount,
            followersCount = 100,
            followingCount = 50
        )
    )
}

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
        authorId = userId, // Added this field
        authorName = user?.username ?: "Usuario",
        authorImageId = R.drawable.messi1,
        gameId = gameId,
        gameImageUrl = if (game?.image?.startsWith("http") == true) game.image else null,
        authorImageUrl = if (user?.profilePictureUrl?.startsWith("http") == true) user.profilePictureUrl else null
    )
}

fun ReviewDTO.toFeedPost(): FeedPost {
    val authorProfile = user?.toUserProfile() ?: UserProfile(
        id = "1",
        username = "Usuario",
        nickName = "@usuario",
        bio = "",
        profileBackgroundId = R.drawable.background_maquinitas,
        profileBgDescription = "",
        profileImageId = R.drawable.messi1,
        profilePictureUrl = null,
        stats = UserStats(0, 0, 0)
    )

    val gameModel = game?.toGame() ?: Game(
        id = gameId,
        title = "Juego",
        developer = "Desarrollador",
        year = 2023,
        image = R.drawable.elden
    )

    return FeedPost(
        id = id,
        author = authorProfile,
        relativeTime = createdAt,
        game = gameModel,
        rating = rating.toDouble(),
        description = description,
        tags = tags,
        likesCount = 12,
        commentsCount = 3
    )
}
