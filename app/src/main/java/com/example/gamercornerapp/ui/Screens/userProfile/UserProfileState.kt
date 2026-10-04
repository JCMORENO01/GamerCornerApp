package com.example.gamercornerapp.ui.Screens.userProfile

import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile

data class UserProfileState(
    val userProfile: UserProfile? = null,
    val reviews: List<ReviewItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
