
package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.UserRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : UserRemoteDataSource {

    override suspend fun getUserById(
        userId: String
    ): UserProfileDTO {
        return service.getUserById(userId)
    }

    override suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    ) {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarado el registro de perfiles"
        )
    }

    override suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO> {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarada la búsqueda de usuarios"
        )
    }

    override suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarada la edición de perfiles"
        )
    }

    override suspend fun updateFCMToken(
        userId: String,
        token: String
    ) {
        throw UnsupportedOperationException(
            "El backend Express no administra tokens FCM"
        )
    }

    override suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    ) {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarado seguir/dejar de seguir"
        )
    }

    override suspend fun isFollowing(
        currentUserId: String,
        targetUserId: String
    ): Boolean {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarada esta consulta"
        )
    }

    override suspend fun getFollowers(
        userId: String
    ): List<String> {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarada la consulta de seguidores"
        )
    }

    override suspend fun getFollowing(
        userId: String
    ): List<String> {
        throw UnsupportedOperationException(
            "El backend Express no tiene declarada la consulta de seguidos"
        )
    }
}
