package com.example.gamercornerapp.ui.model

data class UserProfile(
    val username: String,
    val nickName: String,
    val bio: String,
    val profileBackgroundId: Int,
    val profileBgDescription: String,
    val profileImageId: Int,
    val stats: UserStats
)


data class UserStats (
    val reviewsCount: Int,
    val followersCount: Int,
    val followingCount: Int
)


data class ReviewItem (
    val id: String,
    val gameTitle: String,
    val rating: Int,
    val relativeDate: String,
    val gameImageId: Int,
    val description: String,
    val tags: List<String> = emptyList() //la idea es que al presionar el + el usuario pueda escribir y agregar su propia etiqueta
)
