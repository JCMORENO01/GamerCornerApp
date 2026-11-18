package com.example.gamercornerapp.data.repository

import com.google.firebase.Firebase
import com.google.firebase.ai.Chat
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.FunctionDeclaration
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor() {

    private val createReviewFunction = FunctionDeclaration(
        name = "create_review",
        description = "Crea una reseña (review) de un videojuego en la base de datos de GamerCorner. Úsala cuando el usuario pida explícitamente crear, publicar o hacer una reseña.",
        parameters = mapOf(
            "gameName" to Schema.string(description = "El nombre del videojuego a reseñar."),
            "rating" to Schema.integer(description = "La calificación del juego, un número entero de 1 a 5."),
            "opinion" to Schema.string(description = "El texto de la opinión o reseña.")
        )
    )

    private val getReviewsFunction = FunctionDeclaration(
        name = "get_reviews_for_game",
        description = "Obtiene las reseñas reales publicadas por la comunidad en la base de datos para un videojuego específico. Úsala cuando el usuario pregunte qué opinan las personas, cuáles son las reseñas o la calificación de un juego.",
        parameters = mapOf(
            "gameName" to Schema.string(description = "El nombre del videojuego a consultar.")
        )
    )

    private val systemInstruction = content {
        text("Eres el asistente de Inteligencia Artificial oficial de GamerCorner, una red social exclusiva para gamers, donde los usuarios pueden calificar videojuegos de 1 a 5 estrellas, publicar reseñas, dejar comentarios, explorar los juegos más populares y ver en un mapa las reseñas creadas cerca de su ubicación (GamerCorner Radar). Tu tono debe ser amigable, entusiasta, muy al estilo 'gamer', y siempre dispuesto a ayudar.")
    }

    val generativeModel = Firebase.ai(
        backend = GenerativeBackend.googleAI()
    ).generativeModel(
        modelName = "gemini-3.8-flash",
        systemInstruction = systemInstruction
    )

    private val chat: Chat = generativeModel.startChat()

    suspend fun sendMessage(message: String): String {
        val response = chat.sendMessage(message)

        return response.text?.takeIf { it.isNotBlank() }
            ?: "No recibí una respuesta de la IA. Intenta de nuevo."
    }
}
