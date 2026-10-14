
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: GamerCornerRemoteDataSource
) {

    suspend fun getUserById(
        userId: String
    ): Result<UserProfile> {
        return try {
            val userDto = remoteDataSource.getUserById(userId)
            Result.success(userDto.toUserProfile())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para consultar el estado de seguimiento
    suspend fun getUserWithFollowState(
        userId: String,
        currentUserId: String?
    ): Result<UserProfile> {
        return try {
            val userDto = remoteDataSource.getUserById(userId)
            val profile = userDto.toUserProfile()

            val isFollowed = if (
                !currentUserId.isNullOrBlank() &&
                currentUserId != userId
            ) {
                remoteDataSource.isFollowing(
                    currentUserId,
                    userId
                )
            } else {
                false
            }

            Result.success(
                profile.copy(isFollowed = isFollowed)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para registrar usuario con token FCM
    suspend fun createUserProfile(
        id: String,
        username: String,
        nickName: String,
        bio: String? = null,
        birthDate: String? = null
    ): Result<Unit> {
        return try {
            val token = try {
                FirebaseMessaging.getInstance()
                    .token
                    .await()
            } catch (e: Exception) {
                null
            }

            val dto = UserProfileDTO(
                id = id,
                username = username,
                nickName = nickName,
                bio = bio,
                birthDate = birthDate,
                followersCount = 0L,
                followingCount = 0L,
                FCM_token = token
            )

            remoteDataSource.registerUser(dto, id)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsers(
        query: String
    ): Result<List<UserProfile>> {
        return try {
            val users = remoteDataSource.searchUsers(query)

            Result.success(
                users.map { it.toUserProfile() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para modificar el perfil
    suspend fun updateUserProfile(
        id: String,
        username: String,
        bio: String,
        birthDate: String? = null,
        profilePictureUrl: String? = null
    ): Result<Unit> {
        return try {
            val existingDto = remoteDataSource
                .getUserById(id) as? UserProfileDTO
                ?: throw Exception("No se pudo obtener el perfil")

            val updatedDto = existingDto.copy(
                username = username,
                bio = bio,
                birthDate = birthDate
                    ?: existingDto.birthDate,
                profilePictureUrl = profilePictureUrl
                    ?: existingDto.profilePictureUrl
            )

            remoteDataSource.updateUserProfile(
                id,
                updatedDto
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para actualizar el token FCM
    suspend fun updateFCMToken(
        userId: String
    ): Result<Unit> {
        return try {
            val existingDto = remoteDataSource
                .getUserById(userId) as? UserProfileDTO
                ?: throw Exception("No se pudo obtener el usuario")

            val token = FirebaseMessaging.getInstance()
                .token
                .await()

            val updatedDto = existingDto.copy(
                FCM_token = token
            )

            remoteDataSource.updateUserProfile(
                userId,
                updatedDto
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para seguir o dejar de seguir
    suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    ): Result<Unit> {
        return try {
            require(currentUserId.isNotBlank())
            require(targetUserId.isNotBlank())
            require(currentUserId != targetUserId) {
                "No puedes seguirte a ti mismo"
            }

            remoteDataSource.toggleFollowUser(
                currentUserId,
                targetUserId
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para obtener seguidores
    suspend fun getFollowers(
        userId: String
    ): Result<List<UserProfile>> {
        return try {
            val ids = remoteDataSource.getFollowers(userId)

            val profiles = ids.map { id ->
                remoteDataSource.getUserById(id)
                    .toUserProfile()
            }

            Result.success(profiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para obtener seguidos
    suspend fun getFollowing(
        userId: String
    ): Result<List<UserProfile>> {
        return try {
            val ids = remoteDataSource.getFollowing(userId)

            val profiles = ids.map { id ->
                remoteDataSource.getUserById(id)
                    .toUserProfile()
            }

            Result.success(profiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
