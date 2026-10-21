
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.GameDTO

interface GameRemoteDataSource {

    suspend fun getGames(): List<GameDTO>

    suspend fun getGameById(
        gameId: String
    ): GameDTO
}
