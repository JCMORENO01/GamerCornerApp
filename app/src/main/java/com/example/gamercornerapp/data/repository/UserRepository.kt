package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.toUserProfile
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource
) {

    suspend fun getUserById(userId: String): Result<UserProfile> {
        return try {
            val dto = remoteDataSource.getUserById(userId)
            Result.success(dto.toUserProfile())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
