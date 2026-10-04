package com.example.gamercornerapp.data.dto

data class UserProfileDTO(
    val id: String,
    val username: String,
    val nickName: String,
    val bio: String?,
    val profileBackgroundId: Int,
    val profileBgDescription: String?,
    val profileImageId: Int,
    val profilePictureUrl: String?
)
