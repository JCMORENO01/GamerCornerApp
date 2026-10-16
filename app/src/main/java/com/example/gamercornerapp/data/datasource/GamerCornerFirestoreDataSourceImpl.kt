
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GamerCornerFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : GamerCornerRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> {
        val snapshot = db.collection("games")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(GameDTO::class.java)
                ?.copy(id = doc.id)
        }
    }

    override suspend fun getGameById(gameId: String): GameDTO {
        val doc = db.collection("games")
            .document(gameId)
            .get()
            .await()

        return doc.toObject(GameDTO::class.java)
            ?.copy(id = doc.id)
            ?: throw Exception("No se encontró el videojuego")
    }

    // Función para obtener una reseña con su estado de like
    private suspend fun documentToReview(
        doc: DocumentSnapshot,
        currentUserId: String?
    ): ReviewDTO? {
        val review = doc.toObject(ReviewDTO::class.java)
            ?: return null

        val liked = if (!currentUserId.isNullOrBlank()) {
            doc.reference.collection("likes")
                .document(currentUserId)
                .get()
                .await()
                .exists()
        } else {
            false
        }

        return review.copy(
            id = doc.id,
            isLiked = liked
        )
    }

    // Función para obtener reseñas con sus likes
    private suspend fun mapReviews(
        documents: List<DocumentSnapshot>
    ): List<ReviewDTO> {
        val currentUserId = auth.currentUser?.uid
        val reviews = mutableListOf<ReviewDTO>()

        for (doc in documents) {
            val review = documentToReview(
                doc,
                currentUserId
            )

            if (review != null) {
                reviews.add(review)
            }
        }

        return reviews
    }

    override suspend fun getAllReviews(): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .get()
            .await()

        return mapReviews(snapshot.documents)
    }

    override suspend fun getReviewsByGame(
        gameId: String
    ): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .whereEqualTo("gameId", gameId)
            .get()
            .await()

        return mapReviews(snapshot.documents)
    }

    override suspend fun getReviewsByUser(
        userId: String
    ): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return mapReviews(snapshot.documents)
    }

    override suspend fun createReview(review: CreateReviewDTO) {
        db.collection("reviews")
            .add(review)
            .await()
    }

    override suspend fun updateReview(
        reviewId: String,
        review: CreateReviewDTO
    ) {
        val reviewRef = db.collection("reviews")
            .document(reviewId)

        val original = reviewRef.get().await()

        if (!original.exists()) {
            throw Exception("No se encontró la reseña")
        }

        reviewRef.update(
            mapOf(
                "description" to review.description,
                "rating" to review.rating,
                "tags" to (review.tags ?: emptyList<String>())
            )
        ).await()
    }

    override suspend fun deleteReview(reviewId: String) {
        db.collection("reviews")
            .document(reviewId)
            .delete()
            .await()
    }

    // Función para dar o quitar like
    override suspend fun toggleReviewLike(
        reviewId: String,
        userId: String
    ) {
        require(reviewId.isNotBlank())
        require(userId.isNotBlank())

        val currentUserId = auth.currentUser?.uid
            ?: throw Exception("Usuario no autenticado")

        require(currentUserId == userId) {
            "No puedes dar like por otro usuario"
        }

        val reviewRef = db.collection("reviews")
            .document(reviewId)

        val likeRef = reviewRef.collection("likes")
            .document(userId)

        db.runTransaction { transaction ->
            val reviewDoc = transaction.get(reviewRef)
            val likeDoc = transaction.get(likeRef)

            if (!reviewDoc.exists()) {
                throw Exception("No se encontró la reseña")
            }

            val currentCount = reviewDoc
                .getLong("likesCount") ?: 0L

            if (likeDoc.exists()) {
                transaction.delete(likeRef)

                transaction.update(
                    reviewRef,
                    "likesCount",
                    (currentCount - 1L).coerceAtLeast(0L)
                )
            } else {
                transaction.set(
                    likeRef,
                    mapOf(
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                )

                transaction.update(
                    reviewRef,
                    "likesCount",
                    FieldValue.increment(1L)
                )
            }

            null
        }.await()
    }

    // Función para escuchar reseñas con el estado de like del usuario
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenAllReviews(): Flow<List<ReviewDTO>> =
        callbackFlow<List<DocumentSnapshot>> {
            val listener = db.collection("reviews")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        trySend(snapshot.documents)
                    }
                }

            awaitClose { listener.remove() }
        }.mapLatest { documents ->
            mapReviews(documents)
        }

    override suspend fun getUserById(
        userId: String
    ): UserProfileGenericDTO {
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

    suspend fun createUserProfile(
        userId: String,
        userProfile: UserProfileDTO
    ) {
        registerUser(userProfile, userId)
    }

    // Función para seguir o dejar de seguir
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
                throw Exception("La relación de seguimiento es inconsistente")
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

    // Función para comprobar si sigo a un usuario
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

    // Función para obtener seguidores
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

    // Función para obtener usuarios seguidos
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
}
