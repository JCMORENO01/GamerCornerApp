
package com.example.gamercornerapp.ui.Screens.feed

import com.example.gamercornerapp.data.datasource.AuthRemoteDataSource
import com.example.gamercornerapp.data.datasource.impl.firestore.GameFirestoreDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.GameRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.UUID

class FeedViewModelIntegrationTest {

    private lateinit var viewModel: FeedViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var reviewRepository: ReviewRepository

    private var reviewId: String? = null

    @Before
    fun setUp() {
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
        }

        authRepository = AuthRepository(
            AuthRemoteDataSource(Firebase.auth)
        )

        val userRepository = UserRepository(
            UserFirestoreDataSourceImpl(
                Firebase.firestore,
                Firebase.auth
            )
        )

        val gameRepository = GameRepository(
            GameFirestoreDataSourceImpl(Firebase.firestore)
        )

        reviewRepository = ReviewRepository(
            ReviewFirestoreDataSourceImpl(
                Firebase.firestore,
                Firebase.auth
            ),
            gameRepository,
            userRepository,
            authRepository
        )

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)

        Dispatchers.setMain(dispatcher)

        viewModel = FeedViewModel(
            reviewRepository,
            authRepository,
            userRepository
        )
    }

    // Preparar usuario y reseña para las pruebas
    private suspend fun crearResena(): String {

        val email = "test${UUID.randomUUID()}@test.com"

        val registro = authRepository.signUp(
            email,
            "12345678"
        )

        assertThat(registro.isSuccess).isTrue()

        val review = mapOf(
            "description" to "Excelente videojuego",
            "rating" to 4.5f,
            "tags" to listOf("Action", "RPG"),
            "userId" to Firebase.auth.currentUser!!.uid,
            "gameId" to "test_game",
            "createdAt" to "2026-11-04T12:00:00",
            "user" to mapOf(
                "username" to "GamerTester",
                "nickName" to "gamertest"
            ),
            "game" to mapOf(
                "title" to "Super Game Test"
            ),
            "likesCount" to 0L
        )

        val document = Firebase.firestore
            .collection("reviews")
            .add(review)
            .await()

        reviewId = document.id

        return document.id
    }

    @Test
    fun loadFeed_success_loadsReviewsAndUpdateUI() = runTest {
        // AAA
        // Arrange
        val id = crearResena()

        // Act
        viewModel.loadFeed()

        // Esperar a que aparezca la reseña
        val state = viewModel.uiState.first {
            !it.isLoading && it.posts.any { post ->
                post.id == id
            }
        }

        // Assert
        val post = state.posts.find { it.id == id }

        assertThat(state.isLoading).isFalse()
        assertThat(state.posts).isNotEmpty()
        assertThat(state.errorMessage).isNull()
        assertThat(post).isNotNull()
        assertThat(post?.description)
            .isEqualTo("Excelente videojuego")
    }

    @Test
    fun toggleReviewLike_success_updatesUI() = runTest {
        // AAA
        // Arrange
        val id = crearResena()

        viewModel.loadFeed()

        viewModel.uiState.first {
            it.posts.any { post -> post.id == id }
        }

        // Act
        viewModel.toggleReviewLike(id)

        // Esperar a que se actualice el like
        val state = viewModel.uiState.first {
            it.posts.any { post ->
                post.id == id && post.isLiked
            }
        }

        // Assert
        val post = state.posts.find { it.id == id }

        assertThat(post?.isLiked).isTrue()
        assertThat(post?.likesCount).isEqualTo(1L)
        assertThat(state.errorMessage).isNull()
    }

    @After
    fun tearDown() = runTest {
        try {
            val usuario = Firebase.auth.currentUser
            val id = reviewId

            if (id != null) {
                // Eliminar like si existe
                if (usuario != null) {
                    Firebase.firestore
                        .collection("reviews")
                        .document(id)
                        .collection("likes")
                        .document(usuario.uid)
                        .delete()
                        .await()
                }

                // Eliminar reseña
                Firebase.firestore
                    .collection("reviews")
                    .document(id)
                    .delete()
                    .await()
            }

            if (usuario != null) {
                Firebase.firestore
                    .collection("users")
                    .document(usuario.uid)
                    .delete()
                    .await()

                usuario.delete().await()
            }
        } finally {
            Firebase.auth.signOut()
            Dispatchers.resetMain()
        }
    }
}
