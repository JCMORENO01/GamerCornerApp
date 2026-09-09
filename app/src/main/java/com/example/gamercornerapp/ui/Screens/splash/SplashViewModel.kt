package com.example.gamercornerapp.ui.Screens.splash

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome.asStateFlow()

    private val _navigateToStart = MutableStateFlow(false)
    val navigateToStart: StateFlow<Boolean> = _navigateToStart.asStateFlow()

    init {
        checkUser()
    }

    private fun checkUser() {
        if (authRepository.currentUser != null) {
            _navigateToHome.value = true
        } else {
            _navigateToStart.value = true
        }
    }
}
