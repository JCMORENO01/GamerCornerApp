package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.dto.GameDTO
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class GameFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : GameRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> {
        val snapshot = db.collection("games")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(GameDTO::class.java)
                ?.copy(id = doc.id)
        }
    }

    override suspend fun getGameById(gameId: String): GameDTO {
        val doc = db.collection("games")
            .document(gameId)
            .get()
            .await()

        return doc.toObject(GameDTO::class.java)
            ?.copy(id = doc.id)
            ?: throw Exception("No se encontró el videojuego")
    }

    override suspend fun toggleSaveGame(gameId: String, userId: String) {
        val savedGamesRef = db.collection("users")
            .document(userId)
            .collection("saved_games")
            .document(gameId)
            
        val doc = savedGamesRef.get().await()
        if (doc.exists()) {
            savedGamesRef.delete().await()
        } else {
            savedGamesRef.set(mapOf("timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp())).await()
        }
    }

    override suspend fun isGameSaved(gameId: String, userId: String): Boolean {
        return try {
            db.collection("users")
                .document(userId)
                .collection("saved_games")
                .document(gameId)
                .get()
                .await()
                .exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getSavedGamesByUser(userId: String): List<GameDTO> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("saved_games")
            .get()
            .await()
            
        if (snapshot.isEmpty) return emptyList()
        
        val gameIds = snapshot.documents.map { it.id }
        val games = mutableListOf<GameDTO>()
        val chunks = gameIds.chunked(10)
        
        for (chunk in chunks) {
            val gameDocs = db.collection("games")
                .whereIn(com.google.firebase.firestore.FieldPath.documentId(), chunk)
                .get()
                .await()
                
            games.addAll(
                gameDocs.documents.mapNotNull { doc ->
                    doc.toObject(GameDTO::class.java)?.copy(id = doc.id)
                }
            )
        }
        
        return games
    }
}
