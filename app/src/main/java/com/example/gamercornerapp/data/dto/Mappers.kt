package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats

fun GameDTO.toGame(): Game {
    val imgRes = if (image != 0) image else R.drawable.elden
    return Game(
        id = id.toIntOrNull() ?: 1,
        title = title,
        developer = developer,
        year = year,
        image = imgRes,
        rating = 4.5,
        reviewsCount = 10,
        tags = tags,
        description = description ?: "",
        ratingDistribution = listOf(
            GameRatingBar(5, 0.8f),
            GameRatingBar(4, 0.15f),
            GameRatingBar(3, 0.05f)
        )
    )
}

fun UserProfileDTO.toUserProfile(reviewsCount: Int = 0): UserProfile {
    val bgRes = if (profileBackgroundId != 0) profileBackgroundId else R.drawable.background_maquinitas
    val imgRes = if (profileImageId != 0) profileImageId else R.drawable.messi1
    return UserProfile(
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
    val gameImg = game?.image?.let { if (it != 0) it else R.drawable.mini_elden } ?: R.drawable.mini_elden
    return ReviewItem(
        id = id,
        gameTitle = game?.title ?: "Juego",
        rating = rating.toInt(),
        relativeDate = createdAt,
        gameImageId = gameImg,
        description = description,
        tags = tags
    )
}

fun ReviewDTO.toFeedPost(): FeedPost {
    val authorProfile = user?.toUserProfile() ?: UserProfile(
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
        id = gameId.toIntOrNull() ?: 1,
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
