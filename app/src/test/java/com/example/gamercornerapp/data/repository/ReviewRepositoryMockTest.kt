package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.dto.CreateReviewDTO
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ReviewRepositoryMockTest {

    private lateinit var remoteDataSource: ReviewRemoteDataSource
    private lateinit var gameRepository: GameRepository
    private lateinit var userRepository: UserRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var repository: ReviewRepository

    @Before
    fun setup() {
        remoteDataSource = mockk()
        gameRepository = mockk()
        userRepository = mockk()
        authRepository = mockk()
        
        repository = ReviewRepository(
            remoteDataSource,
            gameRepository,
            userRepository,
            authRepository
        )
    }

    @Test
    fun `getAllReviewsAsFeed success returnsMappedPosts`() = runTest {
        // Given
        val dummyReviews = listOf(
            ReviewDTO(id = "1", description = "Test 1"),
            ReviewDTO(id = "2", description = "Test 2")
        )
        coEvery { remoteDataSource.getAllReviews() } returns dummyReviews

        // When
        val result = repository.getAllReviewsAsFeed()

        // Then
        assertTrue(result.isSuccess)
        val posts = result.getOrNull()
        assertEquals(2, posts?.size)
        assertEquals("Test 1", posts?.get(0)?.description)
    }

    @Test
    fun `getAllReviewsAsFeed failure returnsError`() = runTest {
        // Given
        val exception = Exception("Network Error")
        coEvery { remoteDataSource.getAllReviews() } throws exception

        // When
        val result = repository.getAllReviewsAsFeed()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `createReview unauthenticated returnsError`() = runTest {
        // Given
        val userId = "user1"
        every { authRepository.currentUser } returns null // Not authenticated

        // When
        val result = repository.createReview(
            description = "Great",
            rating = 5f,
            tags = emptyList(),
            userId = userId,
            gameId = "game1"
        )

        // Then
        assertTrue(result.isFailure)
        assertEquals("Usuario no autenticado", result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { remoteDataSource.createReview(any()) }
    }

    @Test
    fun `toggleReviewLike unauthenticated returnsError`() = runTest {
        // Given
        every { authRepository.currentUser } returns null

        // When
        val result = repository.toggleReviewLike("review1", "user1")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Usuario no autenticado", result.exceptionOrNull()?.message)
    }

    @Test
    fun `toggleReviewLike success`() = runTest {
        // Given
        val mockUser = mockk<FirebaseUser>()
        every { mockUser.uid } returns "user1"
        every { authRepository.currentUser } returns mockUser
        coEvery { remoteDataSource.toggleReviewLike("review1", "user1") } returns Unit

        // When
        val result = repository.toggleReviewLike("review1", "user1")

        // Then
        assertTrue(result.isSuccess)
        coVerify { remoteDataSource.toggleReviewLike("review1", "user1") }
    }

    @Test
    fun `deleteReview success`() = runTest {
        // Given
        coEvery { remoteDataSource.deleteReview("review1") } returns Unit

        // When
        val result = repository.deleteReview("review1")

        // Then
        assertTrue(result.isSuccess)
        coVerify { remoteDataSource.deleteReview("review1") }
    }
}
