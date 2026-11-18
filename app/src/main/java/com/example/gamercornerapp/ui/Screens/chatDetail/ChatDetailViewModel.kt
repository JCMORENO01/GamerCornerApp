package com.example.gamercornerapp.ui.Screens.chatDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ChatRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val receiverId: String = checkNotNull(savedStateHandle["userId"])
    
    private val _uiState = MutableStateFlow(ChatDetailUiState())
    val uiState: StateFlow<ChatDetailUiState> = _uiState.asStateFlow()

    init {
        val currentUserId = authRepository.currentUser?.uid ?: ""
        _uiState.update { it.copy(currentUserId = currentUserId) }
        
        loadOtherUser()
        listenMessages(currentUserId)
    }

    private fun loadOtherUser() {
        viewModelScope.launch {
            val result = userRepository.getUserById(receiverId)
            result.onSuccess { user ->
                _uiState.update { it.copy(otherUser = user, isLoadingUser = false) }
            }.onFailure { e ->
                _uiState.update { 
                    it.copy(isLoadingUser = false, errorMessage = "Error cargando usuario: ${e.message}")
                }
            }
        }
    }

    private fun listenMessages(currentUserId: String) {
        val chatId = chatRepository.getChatId(currentUserId, receiverId)
        
        viewModelScope.launch {
            chatRepository.listenMessages(chatId)
                .catch { e ->
                    _uiState.update { 
                        it.copy(isLoadingMessages = false, errorMessage = "Error cargando mensajes: ${e.message}")
                    }
                }
                .collect { messages ->
                    _uiState.update { 
                        it.copy(messages = messages, isLoadingMessages = false)
                    }
                }
        }
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val text = _uiState.value.inputText
        if (text.isBlank()) return
        
        // Limpiamos el input inmediatamente para mejor UX
        _uiState.update { it.copy(inputText = "") }

        viewModelScope.launch {
            val result = chatRepository.sendMessage(receiverId, text)
            result.onFailure { e ->
                // Opcional: restaurar el texto si falla, o mostrar toast
                _uiState.update { it.copy(errorMessage = "Error al enviar: ${e.message}") }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
