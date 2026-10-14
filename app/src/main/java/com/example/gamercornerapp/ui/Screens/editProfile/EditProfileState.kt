package com.example.gamercornerapp.ui.Screens.editProfile

data class EditProfileState(
    val username: String = "",
    val bio: String = "",
    val birthDate: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)