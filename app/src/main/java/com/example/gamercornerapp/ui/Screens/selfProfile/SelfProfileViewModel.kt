package com.example.gamercornerapp.ui.Screens.selfProfile

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelfProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SelfProfileState())
    val uiState: StateFlow<SelfProfileState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                userProfile = LocalDataProvider.userProfile,
                reviews = LocalDataProvider.reviews
            )
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun onLogoutClick() {
        authRepository.signOut()
    }
}
