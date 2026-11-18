package com.example.gamercornerapp.ui.Screens.agent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AgentRepository
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.GameRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.FunctionResponsePart
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AgentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val agentRepository: AgentRepository,
    private val gameRepository: GameRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgentUiState())
    val uiState: StateFlow<AgentUiState> = _uiState.asStateFlow()

    private val chat: Chat = agentRepository.generativeModel.startChat()
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        // Mensaje inicial de bienvenida
        _uiState.update { state ->
            state.copy(
                messages = listOf(
                    ChatMessage(
                        text = "¡Hola! Soy tu asistente IA de GamerCorner. ¿En qué puedo ayudarte hoy?",
                        isFromUser = false
                    )
                )
            )
        }
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun sendMessage() {
        val userText = _uiState.value.inputText
        if (userText.isBlank()) return

        // Agregamos mensaje de usuario e indicamos carga
        _uiState.update { state ->
            val userMsg = ChatMessage(text = userText, isFromUser = true)
            val loadingMsg = ChatMessage(text = "", isFromUser = false, isLoading = true)
            state.copy(
                inputText = "",
                messages = state.messages + userMsg + loadingMsg
            )
        }

        viewModelScope.launch {
            try {
                var response = chat.sendMessage(userText)
                
                // Mientras Gemini pida ejecutar funciones, lo hacemos y le respondemos
                while (response.functionCalls.isNotEmpty()) {
                    val functionCall = response.functionCalls.first()
                    
                    if (functionCall.name == "create_review") {
                        val resultMsg = handleCreateReviewFunction(functionCall.args)
                        
                        // Mandamos el resultado a Gemini para que continue la conversación
                        response = chat.sendMessage(
                            content {
                                part(FunctionResponsePart(functionCall.name, JSONObject(mapOf("result" to resultMsg))))
                            }
                        )
                    } else {
                        // Función desconocida
                        response = chat.sendMessage(
                            content {
                                part(FunctionResponsePart(functionCall.name, JSONObject(mapOf("error" to "Función no encontrada"))))
                            }
                        )
                    }
                }

                // Finalmente mostramos la respuesta de texto de Gemini
                val replyText = response.text ?: "No tengo nada más que decir."
                _uiState.update { state ->
                    val filteredMessages = state.messages.filter { !it.isLoading }
                    state.copy(messages = filteredMessages + ChatMessage(text = replyText, isFromUser = false))
                }

            } catch (e: Exception) {
                _uiState.update { state ->
                    val filteredMessages = state.messages.filter { !it.isLoading }
                    state.copy(
                        messages = filteredMessages,
                        errorMessage = "Error al conectar con la IA: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun handleCreateReviewFunction(args: Map<String, String?>): String {
        val gameName = args["gameName"] ?: return "Error: Falta el nombre del juego."
        val ratingStr = args["rating"] ?: return "Error: Falta la calificación."
        val opinion = args["opinion"] ?: return "Error: Falta la opinión."

        val rating = ratingStr.toFloatOrNull() ?: return "Error: La calificación no es un número válido."

        val userId = authRepository.currentUser?.uid
            ?: return "Error: El usuario no ha iniciado sesión."

        try {
            // 1. Buscar juego por nombre (buscamos en todos los juegos)
            val allGamesResult = gameRepository.getGames()
            if (allGamesResult.isFailure) return "Error buscando el juego: ${allGamesResult.exceptionOrNull()?.message}"
            
            val allGames = allGamesResult.getOrNull() ?: emptyList()
            val game = allGames.find { it.title.contains(gameName, ignoreCase = true) }
            
            if (game == null) return "No se encontró ningún juego llamado '$gameName'. Dile al usuario que verifique el nombre."
            

            // 2. Crear reseña
            val reviewResult = reviewRepository.createReview(
                description = opinion,
                rating = rating,
                tags = listOf("IA-Created"),
                userId = userId,
                gameId = game.id
            )

            return if (reviewResult.isSuccess) {
                "Éxito: Se ha publicado la reseña para '${game.title}' con $rating estrellas. Dile al usuario que fue exitoso."
            } else {
                "Error al guardar la reseña en la base de datos: ${reviewResult.exceptionOrNull()?.message}"
            }
        } catch (e: Exception) {
            return "Excepción inesperada: ${e.localizedMessage}"
        }
    }

    // --- Manejo de Voz (SpeechRecognizer) ---

    fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _uiState.update { it.copy(errorMessage = "Reconocimiento de voz no disponible en este dispositivo.") }
            return
        }

        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}
                    override fun onError(error: Int) {
                        _uiState.update { it.copy(isRecording = false) }
                    }
                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        if (!matches.isNullOrEmpty()) {
                            val spokenText = matches[0]
                            _uiState.update { 
                                it.copy(
                                    inputText = if (it.inputText.isBlank()) spokenText else "${it.inputText} $spokenText",
                                    isRecording = false
                                )
                            }
                        }
                    }
                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES") // Español
        }

        _uiState.update { it.copy(isRecording = true) }
        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        _uiState.update { it.copy(isRecording = false) }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}
