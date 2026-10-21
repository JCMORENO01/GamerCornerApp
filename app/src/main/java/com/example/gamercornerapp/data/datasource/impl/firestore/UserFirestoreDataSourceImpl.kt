
package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.UserRemoteDataSource
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

        val userProfile = doc.toObject(UserProfileDTO::class.java)
            ?.copy(
                id = doc.id,
                followersCount = doc.getLong("followersCount") ?: 0L,
                followingCount = doc.getLong("followingCount") ?: 0L
            )
            ?: throw Exception("No se pudo obtener el usuario")

        return userProfile
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
            .whereGreaterThanOrEqualTo("username", query)
            .whereLessThanOrEqualTo(
                "username",
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
        db.collection("users")
            .document(userId)
            .set(userProfile, SetOptions.merge())
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
