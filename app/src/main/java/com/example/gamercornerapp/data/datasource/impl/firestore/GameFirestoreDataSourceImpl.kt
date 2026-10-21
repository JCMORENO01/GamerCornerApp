
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
}
