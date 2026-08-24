package com.example.gamercornerapp.data

import androidx.annotation.DrawableRes

data class FollowerItem(
    val id: String,
    val username: String,
    val handle: String,
    @DrawableRes val avatarImage: Int,
    val isFollowing: Boolean
)