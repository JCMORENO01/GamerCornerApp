
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO

interface UserRemoteDataSource {

    suspend fun getUserById(
        userId: String
    ): UserProfileDTO

    suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    )

    suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO>

    suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    )

    suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    )

    suspend fun isFollowing(
        currentUserId: String,
        targetUserId: String
    ): Boolean

    suspend fun getFollowers(
        userId: String
    ): List<String>

    suspend fun getFollowing(
        userId: String
    ): List<String>
}
