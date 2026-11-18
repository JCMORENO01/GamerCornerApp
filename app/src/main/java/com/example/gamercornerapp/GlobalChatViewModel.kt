package com.example.gamercornerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        startGlobalChatListener()
    }

    private fun startGlobalChatListener() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            chatRepository.listenMyChats()
                .collect { _ ->
                    // listenMyChats en ChatRepository -> ChatDataSource maneja el disparo de la notificación flotante
                }
        }
    }
}
