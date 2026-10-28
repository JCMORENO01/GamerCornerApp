
package com.example.gamercornerapp.data

data class UserProfile(
    val id: String,
    val username: String,
    val nickName: String,
    val bio: String,
    val birthDate: String? = null,
    val profileBackgroundId: Int,
    val profileBgDescription: String,
    val profileImageId: Int,
    val profilePictureUrl: String? = null,
    val stats: UserStats,

    // Indica si el usuario autenticado sigue este perfil
    val isFollowed: Boolean = false
)

data class UserStats(
    val reviewsCount: Int,
    val followersCount: Int,
    val followingCount: Int
)

data class ReviewItem(
    val id: String,
    val gameTitle: String,
    val rating: Int,
    val relativeDate: String,
    val gameImageId: Int,
    val description: String,
    val tags: List<String> = emptyList(),
    val authorId: String = "1",
    val authorName: String = "Usuario",
    val authorImageId: Int = 0,
    val gameId: String = "1",
    val gameImageUrl: String? = null,
    val authorImageUrl: String? = null,

    // Likes
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    
    // Guardado
    val isBookmarked: Boolean = false
)
