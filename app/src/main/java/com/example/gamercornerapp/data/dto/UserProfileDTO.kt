
package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats

abstract class UserProfileGenericDTO {
    abstract fun toUserProfile(
        reviewsCount: Int = 0
    ): UserProfile
}

data class UserProfileDTO(
    val id: String = "",
    val username: String = "",
    val nickName: String = "",
    val bio: String? = null,
    val birthDate: String? = null,
    val profileBackgroundId: Int = 0,
    val profileBgDescription: String? = null,
    val profileImageId: Int = 0,
    val profilePictureUrl: String? = null,
    val followersCount: Long = 0L,
    val followingCount: Long = 0L,
    val FCM_token: String? = null,
    var isFollowed: Boolean = false
) : UserProfileGenericDTO() {

    constructor() : this(id = "")

    override fun toUserProfile(
        reviewsCount: Int
    ): UserProfile {

        val bgRes = if (profileBackgroundId != 0) {
            profileBackgroundId
        } else {
            R.drawable.background_maquinitas
        }

        val imgRes = if (profileImageId != 0) {
            profileImageId
        } else {
            R.drawable.messi1
        }

        return UserProfile(
            id = id,
            username = username,
            nickName = nickName,
            bio = bio?.takeIf { it.isNotBlank() }
                ?: "No hay biografía",
            birthDate = birthDate,
            profileBackgroundId = bgRes,
            profileBgDescription = profileBgDescription
                ?: "No hay ubicación",
            profileImageId = imgRes,
            profilePictureUrl = profilePictureUrl,
            stats = UserStats(
                reviewsCount = reviewsCount,
                followersCount = followersCount
                    .coerceIn(0L, Int.MAX_VALUE.toLong())
                    .toInt(),
                followingCount = followingCount
                    .coerceIn(0L, Int.MAX_VALUE.toLong())
                    .toInt()
            ),
            isFollowed = isFollowed
        )
    }
}
