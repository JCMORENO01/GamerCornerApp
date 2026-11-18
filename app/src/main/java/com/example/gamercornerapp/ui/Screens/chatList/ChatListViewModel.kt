package com.example.gamercornerapp.ui.Screens.chatList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            chatRepository.listenMyChats()
                .catch { e ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = "Error cargando chats: ${e.message}"
                        ) 
                    }
                }
                .collect { chats ->
                    _uiState.update { 
                        it.copy(
                            chats = chats, 
                            isLoading = false,
                            errorMessage = null
                        ) 
                    }
                }
        }
    }
}
