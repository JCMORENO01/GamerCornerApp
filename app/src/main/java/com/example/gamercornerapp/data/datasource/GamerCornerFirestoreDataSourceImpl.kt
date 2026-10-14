
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.dto.UserProfileGenericDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GamerCornerFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : GamerCornerRemoteDataSource {

    override suspend fun getGames(): List<GameDTO> {
        val snapshot = db.collection("games")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val game = doc.toObject(GameDTO::class.java)
            game?.copy(id = doc.id)
        }
    }

    override suspend fun getGameById(gameId: String): GameDTO {
        val doc = db.collection("games")
            .document(gameId)
            .get()
            .await()

        val game = doc.toObject(GameDTO::class.java)
            ?: throw Exception("No se encontró el videojuego")

        return game.copy(id = doc.id)
    }

    override suspend fun getAllReviews(): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val review = doc.toObject(ReviewDTO::class.java)
            review?.copy(id = doc.id)
        }
    }

    override suspend fun getReviewsByGame(
        gameId: String
    ): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .whereEqualTo("gameId", gameId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val review = doc.toObject(ReviewDTO::class.java)
            review?.copy(id = doc.id)
        }
    }

    override suspend fun getReviewsByUser(
        userId: String
    ): List<ReviewDTO> {
        val snapshot = db.collection("reviews")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val review = doc.toObject(ReviewDTO::class.java)
            review?.copy(id = doc.id)
        }
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

        val updates = mapOf(
            "description" to review.description,
            "rating" to review.rating,
            "tags" to (review.tags ?: emptyList<String>())
        )

        reviewRef.update(updates).await()
    }

    override suspend fun deleteReview(reviewId: String) {
        db.collection("reviews")
            .document(reviewId)
            .delete()
            .await()
    }

    override suspend fun getUserById(
        userId: String
    ): UserProfileGenericDTO {
        val doc = db.collection("users")
            .document(userId)
            .get()
            .await()

        val user = doc.toObject(UserProfileDTO::class.java)
            ?: throw Exception("No se pudo obtener el usuario")

        return user.copy(id = doc.id)
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
        if (query.isBlank()) return emptyList()

        val snapshot = db.collection("users")
            .whereGreaterThanOrEqualTo("username", query)
            .whereLessThanOrEqualTo("username", query + "\uf8ff")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val user = doc.toObject(UserProfileDTO::class.java)
            user?.copy(id = doc.id)
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
}
