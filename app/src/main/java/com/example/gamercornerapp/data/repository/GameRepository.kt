package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.toGame
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource
) {

    suspend fun getGames(): Result<List<Game>> {
        return try {
            val dtos = remoteDataSource.getGames()
            val games = dtos.map { it.toGame() }
            Result.success(games)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }

    suspend fun getGameById(gameId: String): Result<Game> {
        return try {
            val dto = remoteDataSource.getGameById(gameId)
            Result.success(dto.toGame())
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor (${e.code()}): ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }
}
