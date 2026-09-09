package com.example.gamercornerapp.ui.Screens.followers

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.gamercornerapp.data.local.LocalDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class FollowersViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FollowersState())
    val uiState: StateFlow<FollowersState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(followers = LocalDataProvider.followers) }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }
}