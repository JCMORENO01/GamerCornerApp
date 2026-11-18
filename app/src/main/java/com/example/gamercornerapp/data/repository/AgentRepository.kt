package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.FunctionDeclaration
import com.google.ai.client.generativeai.type.Schema
import com.google.ai.client.generativeai.type.Tool
import com.google.ai.client.generativeai.type.content
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor() {

    private val createReviewFunction = FunctionDeclaration(
        name = "create_review",
        description = "Crea una reseña (review) de un videojuego especificado por el usuario. Úsala cuando el usuario te pida explícitamente hacer, crear o publicar una reseña de un juego.",
        parameters = listOf(
            Schema.str(
                name = "gameName",
                description = "El nombre del videojuego a reseñar. Intenta que sea el nombre exacto, ej. 'Elden Ring', 'Cyberpunk 2077'."
            ),
            Schema.int(
                name = "rating",
                description = "La calificación del juego, un número entero del 1 al 5."
            ),
            Schema.str(
                name = "opinion",
                description = "El texto de la opinión o crítica sobre el videojuego."
            )
        ),
        requiredParameters = listOf("gameName", "rating", "opinion")
    )

    private val systemInstruction = content {
        text("Eres el asistente de Inteligencia Artificial oficial de GamerCorner, una red social exclusiva para gamers, donde los usuarios pueden calificar videojuegos de 1 a 5 estrellas, publicar reseñas, dejar comentarios, explorar los juegos más populares y ver en un mapa las reseñas creadas cerca de su ubicación (GamerCorner Radar). Tu tono debe ser amigable, entusiasta, muy al estilo 'gamer', y siempre dispuesto a ayudar. Puedes responder preguntas sobre la plataforma y, si el usuario te lo pide, puedes crear reseñas de videojuegos invocando la función correspondiente.")
    }

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction = systemInstruction,
        tools = listOf(
            Tool(functionDeclarations = listOf(createReviewFunction))
        )
    )
}
