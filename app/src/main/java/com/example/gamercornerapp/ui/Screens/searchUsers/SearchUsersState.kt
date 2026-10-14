package com.example.gamercornerapp.ui.Screens.searchUsers

import com.example.gamercornerapp.data.UserProfile

data class SearchUsersState(
    val query: String = "",
    val searchResults: List<UserProfile> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)