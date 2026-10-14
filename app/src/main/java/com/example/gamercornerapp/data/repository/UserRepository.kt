package com.example.gamercornerapp.data.repository

import android.util.Log
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.UserProfileDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource
) {

    suspend fun getUserById(userId: String): Result<UserProfile> {
        return try {
            val userDto = remoteDataSource.getUserById(userId)
            val userProfile = userDto.toUserProfile()
            Result.success(userProfile)
        } catch (e: Exception) {
            Log.d("TAG", "getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun createUserProfile(
        id: String,
        username: String,
        nickName: String,
        bio: String? = null,
        birthDate: String? = null
    ): Result<Unit> {
        return try {
            val dto = UserProfileDTO(
                id = id,
                username = username,
                nickName = nickName,
                bio = bio,
                birthDate = birthDate
            )
            remoteDataSource.registerUser(dto, id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsers(query: String): Result<List<UserProfile>> {
        return try {
            val dtos = remoteDataSource.searchUsers(query)
            Result.success(dtos.map { it.toUserProfile() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(
        id: String,
        username: String,
        bio: String,
        birthDate: String? = null,
        profilePictureUrl: String? = null
    ): Result<Unit> {
        return try {
            val currentUserDto = remoteDataSource.getUserById(id)
            val currentProfile = currentUserDto.toUserProfile()
            val updatedDto = UserProfileDTO(
                id = id,
                username = username,
                nickName = currentProfile.nickName,
                bio = bio,
                birthDate = birthDate ?: currentProfile.birthDate,
                profilePictureUrl = profilePictureUrl ?: currentProfile.profilePictureUrl
            )
            remoteDataSource.updateUserProfile(id, updatedDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
