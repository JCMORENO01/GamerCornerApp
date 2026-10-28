
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.GameDTO

interface GameRemoteDataSource {

    suspend fun getGames(): List<GameDTO>

    suspend fun getGameById(
        gameId: String
    ): GameDTO

    suspend fun toggleSaveGame(
        gameId: String,
        userId: String
    )

    suspend fun isGameSaved(
        gameId: String,
        userId: String
    ): Boolean

    suspend fun getSavedGamesByUser(
        userId: String
    ): List<GameDTO>
}
