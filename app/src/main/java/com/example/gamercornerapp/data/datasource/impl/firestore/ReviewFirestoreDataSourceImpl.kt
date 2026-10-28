
package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ReviewRemoteDataSource {

    private suspend fun documentToReview(
        doc: DocumentSnapshot,
        currentUserId: String?
    ): ReviewDTO? {
        val review = doc.toObject(ReviewDTO::class.java)
            ?: return null

        val likesCountFromDoc = doc.getLong("likesCount")
            ?: review.likesCount

        val liked = if (!currentUserId.isNullOrBlank()) {
            try {
                doc.reference.collection("likes")
                    .document(currentUserId)
                    .get()
                    .await()
                    .exists()
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }

        val bookmarked = if (!currentUserId.isNullOrBlank()) {
            try {
                db.collection("users")
                    .document(currentUserId)
                    .collection("saved_reviews")
                    .document(doc.id)
                    .get()
                    .await()
                    .exists()
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }

        return review.copy(
            id = doc.id,
            likesCount = likesCountFromDoc,
            isLiked = liked,
            isBookmarked = bookmarked
        )
    }

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

    override suspend fun createReview(
        review: CreateReviewDTO
    ) {
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

    override suspend fun deleteReview(
        reviewId: String
    ) {
        db.collection("reviews")
            .document(reviewId)
            .delete()
            .await()
    }

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

            val currentCount =
                reviewDoc.getLong("likesCount") ?: 0L

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

    override suspend fun toggleSaveReview(
        reviewId: String,
        userId: String
    ) {
        require(reviewId.isNotBlank())
        require(userId.isNotBlank())

        val currentUserId = auth.currentUser?.uid
            ?: throw Exception("Usuario no autenticado")

        require(currentUserId == userId) {
            "No puedes guardar reseñas por otro usuario"
        }

        val bookmarkRef = db.collection("users")
            .document(userId)
            .collection("saved_reviews")
            .document(reviewId)

        val bookmarkDoc = bookmarkRef.get().await()

        if (bookmarkDoc.exists()) {
            bookmarkRef.delete().await()
        } else {
            bookmarkRef.set(
                mapOf("timestamp" to FieldValue.serverTimestamp())
            ).await()
        }
    }

    override suspend fun getSavedReviewsByUser(
        userId: String
    ): List<ReviewDTO> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("saved_reviews")
            .get()
            .await()
            
        if (snapshot.isEmpty) return emptyList()
        
        val reviewIds = snapshot.documents.map { it.id }
        
        // Firestore 'in' query has a limit of 10. For this example, we chunk it.
        val reviews = mutableListOf<ReviewDTO>()
        val chunks = reviewIds.chunked(10)
        
        for (chunk in chunks) {
            val reviewDocs = db.collection("reviews")
                .whereIn(com.google.firebase.firestore.FieldPath.documentId(), chunk)
                .get()
                .await()
                
            reviews.addAll(mapReviews(reviewDocs.documents))
        }
        
        return reviews
    }

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

            awaitClose {
                listener.remove()
            }
        }.mapLatest { documents ->
            mapReviews(documents)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenReviewsByGame(gameId: String): Flow<List<ReviewDTO>> =
        callbackFlow<List<DocumentSnapshot>> {
            val listener = db.collection("reviews")
                .whereEqualTo("gameId", gameId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        trySend(snapshot.documents)
                    }
                }

            awaitClose {
                listener.remove()
            }
        }.mapLatest { documents ->
            mapReviews(documents)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenReviewsByUser(userId: String): Flow<List<ReviewDTO>> =
        callbackFlow<List<DocumentSnapshot>> {
            val listener = db.collection("reviews")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        trySend(snapshot.documents)
                    }
                }

            awaitClose {
                listener.remove()
            }
        }.mapLatest { documents ->
            mapReviews(documents)
        }
}
