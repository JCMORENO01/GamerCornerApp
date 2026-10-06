package com.example.gamercornerapp.data

data class UserProfile(
    val id: String, // NEW FIELD
    val username: String,
    val nickName: String,
    val bio: String,
    val profileBackgroundId: Int,
    val profileBgDescription: String,
    val profileImageId: Int,
    val profilePictureUrl: String? = null,
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
    val tags: List<String> = emptyList(), //la idea es que al presionar el + el usuario pueda escribir y agregar su propia etiqueta
    val authorId: String = "1", // NEW FIELD
    val authorName: String = "Usuario",
    val authorImageId: Int = 0 // Add these fields to display author in Game Detail
)
