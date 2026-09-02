package com.example.gamercornerapp.ui.Screens.selfProfile

import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile

data class SelfProfileState(
    val userProfile: UserProfile? = null,
    val reviews: List<ReviewItem> = emptyList(),
    val selectedTabIndex: Int = 0
)