
package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.GameDTO
import javax.inject.Inject

class GameRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : GameRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> {
        return service.getGames()
    }

    override suspend fun getGameById(
        gameId: String
    ): GameDTO {
        return service.getGameById(gameId)
    }
}
