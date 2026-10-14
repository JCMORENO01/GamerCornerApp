
package com.example.gamercornerapp.ui.Screens.followers

import com.example.gamercornerapp.data.FollowerItem

data class FollowersState(
    val followers: List<FollowerItem> = emptyList(),
    val following: List<FollowerItem> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
