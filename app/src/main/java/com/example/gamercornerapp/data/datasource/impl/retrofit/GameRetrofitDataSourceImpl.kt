package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.GameDTO
import javax.inject.Inject

class GameRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : GameRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> =
        service.getGamesRaw().map { it.toGameDTO() }

    override suspend fun getGameById(gameId: String): GameDTO =
        service.getGameByIdRaw(gameId).toGameDTO()
        
    override suspend fun toggleSaveGame(gameId: String, userId: String) {
    }

    override suspend fun isGameSaved(gameId: String, userId: String): Boolean {
        return false
    }

    override suspend fun getSavedGamesByUser(userId: String): List<GameDTO> {
        return emptyList()
    }
}