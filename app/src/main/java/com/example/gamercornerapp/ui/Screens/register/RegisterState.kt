package com.example.gamercornerapp.ui.Screens.register

data class RegisterState(
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthDate: String = "",
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val termsAccepted: Boolean = false,
    val errorRes: Int? = null,
    val showError: Boolean = false,
    val navigateToFeed: Boolean = false
)