package com.example.gamercornerapp.data.datasource.impl.retrofit

import com.example.gamercornerapp.data.datasource.UserRemoteDataSource
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.example.gamercornerapp.data.dto.UserProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    private val service: GamerCornerService
) : UserRemoteDataSource {

    override suspend fun getUserById(
        userId: String
    ): UserProfileDTO =
        service.getUserByIdRaw(userId).toUserProfileDTO()

    override suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    ) {
        service.registerUserRaw(
            registerUserDto.toBackendBody(userId)
        )
    }

    override suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO> =
        service.searchUsersRaw(query).map {
            it.toUserProfileDTO()
        }

    override suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        service.updateUserRaw(
            userId,
            userProfile.toBackendBody(userId)
        )
    }

    override suspend fun updateFCMToken(
        userId: String,
        token: String
    ) {
        service.updateFcmTokenRaw(
            userId,
            mapOf("token" to token)
        )
    }

    override suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    ) {
        val status = service.isFollowingRaw(
            targetUserId,
            currentUserId
        )

        val body = mapOf(
            "currentUserId" to currentUserId
        )

        if (status.bool("isFollowing")) {
            service.unfollowUserRaw(targetUserId, body)
        } else {
            service.followUserRaw(targetUserId, body)
        }
    }

    override suspend fun isFollowing(
        currentUserId: String,
        targetUserId: String
    ): Boolean =
        service.isFollowingRaw(
            targetUserId,
            currentUserId
        ).bool("isFollowing")

    override suspend fun getFollowers(
        userId: String
    ): List<String> =
        service.getFollowersRaw(userId).map {
            it.str("uid").ifBlank {
                it.str("id")
            }
        }

    override suspend fun getFollowing(
        userId: String
    ): List<String> =
        service.getFollowingRaw(userId).map {
            it.str("uid").ifBlank {
                it.str("id")
            }
        }
    override fun listenUserById(
        userId: String
    ): Flow<UserProfileDTO> =
        flow {
            emit(getUserById(userId))
        }
}