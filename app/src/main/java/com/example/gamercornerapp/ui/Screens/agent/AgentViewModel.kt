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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private var speechRecognizer: SpeechRecognizer? = null

    init {
        _uiState.update { state ->
            state.copy(
                messages = listOf(
                    ChatMessage(
                        text = "¡Hola! Soy tu asistente IA de GamerCorner. ¿En qué puedo ayudarte hoy? Puedes pedirme opiniones de juegos o pedirme que publique una reseña por ti.",
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

        _uiState.update { state ->
            val userMsg = ChatMessage(
                text = userText,
                isFromUser = true
            )

            val loadingMsg = ChatMessage(
                text = "",
                isFromUser = false,
                isLoading = true
            )

            state.copy(
                inputText = "",
                messages = state.messages + userMsg + loadingMsg
            )
        }

        executeChatRequest(userText)
    }

    private fun executeChatRequest(userText: String) {
        viewModelScope.launch {
            try {
                val promptToSend = buildEnrichedPrompt(userText)
                val rawReplyText = agentRepository.sendMessage(promptToSend)

                val (cleanReplyText, actionMessage) = processActionIfPresent(rawReplyText)
                val finalMessage = if (!actionMessage.isNullOrBlank()) {
                    "$cleanReplyText\n\n$actionMessage"
                } else {
                    cleanReplyText
                }

                _uiState.update { state ->
                    val filteredMessages =
                        state.messages.filter { !it.isLoading }

                    state.copy(
                        messages = filteredMessages + ChatMessage(
                            text = finalMessage,
                            isFromUser = false
                        )
                    )
                }

            } catch (e: Exception) {

                _uiState.update { state ->
                    val filteredMessages =
                        state.messages.filter { !it.isLoading }

                    state.copy(
                        messages = filteredMessages,
                        errorMessage = "No se pudo conectar con el asistente IA. " +
                                "Revisa tu conexión y la configuración de Firebase AI Logic. " +
                                "Detalle: ${e.localizedMessage ?: e.javaClass.simpleName}"
                    )
                }
            }
        }
    }

    private suspend fun processActionIfPresent(rawText: String): Pair<String, String?> {
        val regex = Regex("""\[ACCION_CREAR_RESEÑA:\s*JUEGO="([^"]+)",\s*CALIFICACION="([^"]+)",\s*OPINION="([^"]+)"]""")
        val match = regex.find(rawText) ?: return Pair(rawText, null)

        val gameName = match.groupValues[1]
        val ratingStr = match.groupValues[2]
        val opinion = match.groupValues[3]

        val cleanText = rawText.replace(match.value, "").trim()
        val actionResult = handleCreateReview(gameName, ratingStr, opinion)

        return Pair(cleanText, actionResult)
    }

    private suspend fun handleCreateReview(gameName: String, ratingStr: String, opinion: String): String {
        val rating = ratingStr.toFloatOrNull() ?: 5.0f
        val userId = authRepository.currentUser?.uid
            ?: return "⚠️ No se pudo publicar automáticamente porque no has iniciado sesión."

        val gamesResult = gameRepository.getGames()
        val games = gamesResult.getOrNull() ?: emptyList()
        val game = games.find { it.title.contains(gameName, ignoreCase = true) }
            ?: return "⚠️ No se encontró el videojuego '$gameName' en el catálogo para publicar la reseña."

        val result = reviewRepository.createReview(
            description = opinion,
            rating = rating,
            tags = listOf("IA-Assistant"),
            userId = userId,
            gameId = game.id
        )

        return if (result.isSuccess) {
            "✅ Reseña publicada con éxito en GamerCorner para '${game.title}' con $rating ⭐ estrellas."
        } else {
            "⚠️ Error al guardar la reseña en Firestore: ${result.exceptionOrNull()?.message}"
        }
    }

    private suspend fun buildEnrichedPrompt(userText: String): String {
        try {
            val reviewsResult = reviewRepository.getAllReviewsAsFeed()
            val reviews = reviewsResult.getOrNull() ?: emptyList()

            val gamesResult = gameRepository.getGames()
            val games = gamesResult.getOrNull() ?: emptyList()

            val formattedReviews = if (reviews.isNotEmpty()) {
                reviews.take(15).joinToString("\n") { post ->
                    "- Juego: '${post.game.title}', Autor: @${post.author.nickName} (${post.author.username}), Puntuación: ${post.rating}/5, Fecha: ${post.relativeTime}, Opinión: '${post.description}'"
                }
            } else {
                "No hay reseñas guardadas en la base de datos."
            }

            val formattedGames = if (games.isNotEmpty()) {
                games.take(15).joinToString("\n") { game ->
                    "- Juego: '${game.title}', Desarrollador: ${game.developer}, Calificación promedio: ${game.rating}/5"
                }
            } else {
                "No hay juegos guardados."
            }

            return """
            [SISTEMA - DATOS DE FIRESTORE EN TIEMPO REAL DE GAMERCORNER]
            TIENES ACCESO DIRECTO A LA BASE DE DATOS EN VIVO DE GAMERCORNER.
            A continuación se encuentran las reseñas y juegos actuales consultados directamente de Firestore:

            --- CATÁLOGO DE JUEGOS DISPONIBLES ---
            $formattedGames

            --- RESEÑAS PUBLICADAS EN EL FEED (De más reciente a más antigua) ---
            $formattedReviews

            [INSTRUCCIONES OBLIGATORIAS]
            1. TIENES ACCESO A LA BASE DE DATOS DE FIRESTORE. NUNCA digas que no tienes acceso a la base de datos o al servidor.
            2. Usa los datos reales de arriba para responder a la consulta del usuario.
            3. Si el usuario pregunta por la última reseña publicada, identifica la primera de la lista de reseñas anteriores.
            4. FORMATO LIMPIO: Escribe respuestas muy limpias, fluidas y fáciles de leer en pantalla móvil. NO USES encabezados de markdown con símbolos de almohadilla (como ### o ##). NO USES hashtags al azar. Usa negrita (**texto**) o viñetas simples (•) para estructurar el contenido.
            5. PUBLICAR / CREAR RESEÑAS: Si el usuario te pide crear, publicar, subir o redactar una reseña (o si te pide ayuda para construirla y confirma que la publique), DEBES publicar la reseña incluyendo al FINAL de tu respuesta la siguiente etiqueta exacta:
               [ACCION_CREAR_RESEÑA: JUEGO="NombreExactoDelJuego", CALIFICACION="Número1a5", OPINION="TextoDeLaOpinion"]

               Ejemplo:
               "¡Excelente bro! Ya redactamos tu reseña para Elden Ring.
               [ACCION_CREAR_RESEÑA: JUEGO="Elden Ring", CALIFICACION="5", OPINION="Un juego de mundo abierto espectacular."]"

            6. Responde siempre con un tono muy amigable, entusiasta y gamer.

            Mensaje del usuario: $userText
            """.trimIndent()
        } catch (_: Exception) {
            return userText
        }
    }

    // -----------------------------
    // RECONOCIMIENTO DE VOZ
    // -----------------------------

    fun startListening() {

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _uiState.update {
                it.copy(
                    errorMessage = "Reconocimiento de voz no disponible en este dispositivo."
                )
            }
            return
        }

        if (speechRecognizer == null) {

            speechRecognizer =
                SpeechRecognizer.createSpeechRecognizer(context).apply {

                    setRecognitionListener(object : RecognitionListener {

                        override fun onReadyForSpeech(params: Bundle?) {}

                        override fun onBeginningOfSpeech() {}

                        override fun onRmsChanged(rmsdB: Float) {}

                        override fun onBufferReceived(buffer: ByteArray?) {}

                        override fun onEndOfSpeech() {}

                        override fun onError(error: Int) {
                            _uiState.update {
                                it.copy(isRecording = false)
                            }
                        }

                        override fun onResults(results: Bundle?) {

                            val matches = results?.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION
                            )

                            if (!matches.isNullOrEmpty()) {

                                val spokenText = matches[0]

                                _uiState.update {
                                    it.copy(
                                        inputText = if (it.inputText.isBlank()) {
                                            spokenText
                                        } else {
                                            "${it.inputText} $spokenText"
                                        },
                                        isRecording = false
                                    )
                                }
                            }
                        }

                        override fun onPartialResults(
                            partialResults: Bundle?
                        ) {}

                        override fun onEvent(
                            eventType: Int,
                            params: Bundle?
                        ) {}
                    })
                }
        }

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "es-ES"
            )
        }

        _uiState.update {
            it.copy(isRecording = true)
        }

        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()

        _uiState.update {
            it.copy(isRecording = false)
        }
    }

    override fun onCleared() {
        super.onCleared()

        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}
