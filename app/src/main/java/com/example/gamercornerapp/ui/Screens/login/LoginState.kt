package com.example.gamercornerapp.ui.Screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLogoUp: Boolean = false,
    val errorRes: Int? = null,
    val showError: Boolean = false,
    val navigateToFeed: Boolean = false
)