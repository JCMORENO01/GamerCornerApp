
package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.UserRemoteDataSource
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserRemoteDataSource {

    override suspend fun getUserById(
        userId: String
    ): UserProfileDTO {
        val doc = db.collection("users")
            .document(userId)
            .get()
            .await()

        if (!doc.exists()) {
            val currentUser = auth.currentUser
            if (currentUser != null && currentUser.uid == userId) {
                val emailName = currentUser.email?.substringBefore("@")
                    ?.takeIf { it.isNotBlank() } ?: "Usuario"
                val displayName = currentUser.displayName?.takeIf { it.isNotBlank() }
                    ?: emailName
                val photoUrl = currentUser.photoUrl?.toString()

                val defaultDto = UserProfileDTO(
                    id = userId,
                    username = displayName,
                    nickName = emailName,
                    bio = "¡Hola! Estoy usando GamerCorner.",
                    profilePictureUrl = photoUrl,
                    followersCount = 0L,
                    followingCount = 0L
                )

                try {
                    db.collection("users")
                        .document(userId)
                        .set(defaultDto)
                        .await()
                } catch (e: Exception) {
                    // Si falla la escritura por permisos/red, retornamos el DTO de todas formas
                }

                return defaultDto
            } else {
                throw Exception("No se encontró el perfil de usuario")
            }
        }

        val userProfile = try {
            doc.toObject(UserProfileDTO::class.java)
        } catch (e: Exception) {
            null
        } ?: UserProfileDTO(
            id = doc.id,
            username = doc.getString("username")
                ?: doc.getString("fullName")
                ?: "",
            nickName = doc.getString("nickName")
                ?: doc.getString("nickname")
                ?: "",
            bio = doc.getString("bio"),
            birthDate = doc.getString("birthDate"),
            profileBackgroundId = (doc.getLong("profileBackgroundId") ?: 0L).toInt(),
            profileBgDescription = doc.getString("profileBgDescription"),
            profileImageId = (doc.getLong("profileImageId") ?: 0L).toInt(),
            profilePictureUrl = doc.getString("profilePictureUrl"),
            followersCount = doc.getLong("followersCount") ?: 0L,
            followingCount = doc.getLong("followingCount") ?: 0L
        )

        return userProfile.copy(
            id = doc.id,
            followersCount = doc.getLong("followersCount") ?: userProfile.followersCount,
            followingCount = doc.getLong("followingCount") ?: userProfile.followingCount
        )
    }

    override suspend fun registerUser(
        registerUserDto: UserProfileDTO,
        userId: String
    ) {
        db.collection("users")
            .document(userId)
            .set(registerUserDto)
            .await()
    }

    override suspend fun searchUsers(
        query: String
    ): List<UserProfileDTO> {
        if (query.isBlank()) {
            return emptyList()
        }

        val snapshot = db.collection("users")
            .whereGreaterThanOrEqualTo("nickName", query)
            .whereLessThanOrEqualTo(
                "nickName",
                query + "\uf8ff"
            )
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(UserProfileDTO::class.java)
                ?.copy(id = doc.id)
        }
    }

    override suspend fun updateUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        require(auth.currentUser?.uid == userId) {
            "Usuario no autenticado"
        }

        db.collection("users")
            .document(userId)
            .update(
                mapOf(
                    "username" to userProfile.username,
                    "nickName" to userProfile.nickName,
                    "bio" to userProfile.bio,
                    "birthDate" to userProfile.birthDate,
                    "profileBackgroundId" to userProfile.profileBackgroundId,
                    "profileBgDescription" to userProfile.profileBgDescription,
                    "profileImageId" to userProfile.profileImageId,
                    "profilePictureUrl" to userProfile.profilePictureUrl
                )
            )
            .await()
    }

    override suspend fun updateFCMToken(
        userId: String,
        token: String
    ) {
        require(auth.currentUser?.uid == userId) {
            "Usuario no autenticado"
        }

        db.collection("users")
            .document(userId)
            .update("fcm_token", token)
            .await()
    }

    override suspend fun toggleFollowUser(
        currentUserId: String,
        targetUserId: String
    ) {
        require(currentUserId.isNotBlank())
        require(targetUserId.isNotBlank())

        require(currentUserId != targetUserId) {
            "No puedes seguirte a ti mismo"
        }

        require(auth.currentUser?.uid == currentUserId) {
            "Usuario no autenticado"
        }

        val currentUserRef = db.collection("users")
            .document(currentUserId)

        val targetUserRef = db.collection("users")
            .document(targetUserId)

        val followingRef = currentUserRef
            .collection("following")
            .document(targetUserId)

        val followersRef = targetUserRef
            .collection("followers")
            .document(currentUserId)

        db.runTransaction { transaction ->

            val currentUserDoc = transaction.get(currentUserRef)
            val targetUserDoc = transaction.get(targetUserRef)
            val followingDoc = transaction.get(followingRef)
            val followersDoc = transaction.get(followersRef)

            if (!currentUserDoc.exists() || !targetUserDoc.exists()) {
                throw Exception("No se encontró el usuario")
            }

            if (followingDoc.exists() != followersDoc.exists()) {
                throw Exception(
                    "La relación de seguimiento es inconsistente"
                )
            }

            if (followingDoc.exists()) {
                transaction.delete(followingRef)
                transaction.delete(followersRef)

                val followingCount =
                    currentUserDoc.getLong("followingCount") ?: 0L

                val followersCount =
                    targetUserDoc.getLong("followersCount") ?: 0L

                transaction.update(
                    currentUserRef,
                    "followingCount",
                    (followingCount - 1L).coerceAtLeast(0L)
                )

                transaction.update(
                    targetUserRef,
                    "followersCount",
                    (followersCount - 1L).coerceAtLeast(0L)
                )
            } else {
                val data = mapOf(
                    "timestamp" to FieldValue.serverTimestamp()
                )

                transaction.set(followingRef, data)
                transaction.set(followersRef, data)

                transaction.update(
                    currentUserRef,
                    "followingCount",
                    FieldValue.increment(1L)
                )

                transaction.update(
                    targetUserRef,
                    "followersCount",
                    FieldValue.increment(1L)
                )
            }

            null
        }.await()
    }

    override suspend fun isFollowing(
        currentUserId: String,
        targetUserId: String
    ): Boolean {
        if (currentUserId.isBlank() || targetUserId.isBlank()) {
            return false
        }

        return db.collection("users")
            .document(currentUserId)
            .collection("following")
            .document(targetUserId)
            .get()
            .await()
            .exists()
    }

    override suspend fun getFollowers(
        userId: String
    ): List<String> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("followers")
            .get()
            .await()

        return snapshot.documents.map { it.id }
    }

    override suspend fun getFollowing(
        userId: String
    ): List<String> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("following")
            .get()
            .await()

        return snapshot.documents.map { it.id }
    }

    suspend fun createUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        registerUser(userProfile, userId)
    }
}
