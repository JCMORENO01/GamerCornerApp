
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.dto.toGame
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource
) {

    suspend fun getGames(): Result<List<Game>> {
        return try {
            val dtos = remoteDataSource.getGames()
            Result.success(dtos.map { it.toGame() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGameById(gameId: String): Result<Game> {
        return try {
            val dto = remoteDataSource.getGameById(gameId)
            Result.success(dto.toGame())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleSaveGame(gameId: String, userId: String): Result<Unit> {
        return try {
            remoteDataSource.toggleSaveGame(gameId, userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isGameSaved(gameId: String, userId: String): Result<Boolean> {
        return try {
            val isSaved = remoteDataSource.isGameSaved(gameId, userId)
            Result.success(isSaved)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSavedGamesByUser(userId: String): Result<List<Game>> {
        return try {
            val dtos = remoteDataSource.getSavedGamesByUser(userId)
            Result.success(dtos.map { it.toGame() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
