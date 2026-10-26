package com.example.gamercornerapp.data.util

import com.example.gamercornerapp.data.dto.GameDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.github.javafaker.Faker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

object FakerDataGenerator {
    private val faker = Faker()

    fun generateFakeUserProfile(id: String = UUID.randomUUID().toString()): UserProfileDTO {
        return UserProfileDTO(
            id = id,
            username = faker.name().username(),
            nickName = "@${faker.name().firstName().lowercase()}",
            bio = faker.lorem().sentence(),
            followersCount = faker.number().numberBetween(0L, 500L),
            followingCount = faker.number().numberBetween(0L, 300L)
        )
    }

    fun generateFakeGame(id: String = UUID.randomUUID().toString()): GameDTO {
        return GameDTO(
            id = id,
            title = faker.book().title(),
            developer = faker.company().name(),
            year = faker.number().numberBetween(2010, 2026),
            image = "https://example.com/cover.jpg",
            description = faker.lorem().paragraph(),
            rating = faker.number().randomDouble(1, 1, 5),
            reviewsCount = faker.number().numberBetween(0, 150)
        )
    }

    fun generateFakeReview(id: String = UUID.randomUUID().toString(), userId: String, gameId: String): ReviewDTO {
        return ReviewDTO(
            id = id,
            description = faker.lorem().sentence(),
            rating = faker.number().numberBetween(1, 5).toFloat(),
            userId = userId,
            gameId = gameId,
            createdAt = "Hace un momento"
        )
    }

    suspend fun seedDatabase(firestore: FirebaseFirestore) {
        val user = generateFakeUserProfile("user_seed_1")
        val game = generateFakeGame("game_seed_1")
        val review = generateFakeReview("review_seed_1", user.id, game.id)

        firestore.collection("users").document(user.id).set(user).await()
        firestore.collection("games").document(game.id).set(game).await()
        firestore.collection("reviews").document(review.id).set(review).await()
    }
}
