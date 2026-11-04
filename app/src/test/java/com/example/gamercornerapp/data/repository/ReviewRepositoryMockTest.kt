package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    fun getAllReviewsAsFeed_success_returnsMappedPosts() = runTest {
        //AAA
        //arrange
        val dummyReviews = listOf(
            ReviewDTO(id = "1", description = "Test 1"),
            ReviewDTO(id = "2", description = "Test 2")
        )
        coEvery { remoteDataSource.getAllReviews() } returns dummyReviews

        //act
        val result = repository.getAllReviewsAsFeed()

        //assert
        assertThat(result.isSuccess).isTrue()
        val posts = result.getOrNull()
        assertThat(posts?.size).isEqualTo(2)
        assertThat(posts?.get(0)?.description).isEqualTo("Test 1")
    }

    @Test
    fun getAllReviewsAsFeed_failure_returnsError() = runTest {
        //AAA
        //arrange
        val exception = Exception("Network Error")
        coEvery { remoteDataSource.getAllReviews() } throws exception

        //act
        val result = repository.getAllReviewsAsFeed()

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network Error")
    }

    @Test
    fun createReview_unauthenticated_returnsError() = runTest {
        //AAA
        //arrange
        val userId = "user1"
        every { authRepository.currentUser } returns null

        //act
        val result = repository.createReview(
            description = "Great",
            rating = 5f,
            tags = emptyList(),
            userId = userId,
            gameId = "game1"
        )

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Usuario no autenticado")
        coVerify(exactly = 0) { remoteDataSource.createReview(any()) }
    }

    @Test
    fun toggleReviewLike_unauthenticated_returnsError() = runTest {
        //AAA
        //arrange
        every { authRepository.currentUser } returns null

        //act
        val result = repository.toggleReviewLike("review1", "user1")

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Usuario no autenticado")
    }

    @Test
    fun toggleReviewLike_success() = runTest {
        //AAA
        //arrange
        val mockUser = mockk<FirebaseUser>()
        every { mockUser.uid } returns "user1"
        every { authRepository.currentUser } returns mockUser
        coEvery { remoteDataSource.toggleReviewLike("review1", "user1") } returns Unit

        //act
        val result = repository.toggleReviewLike("review1", "user1")

        //assert
        assertThat(result.isSuccess).isTrue()
        coVerify { remoteDataSource.toggleReviewLike("review1", "user1") }
    }

    @Test
    fun deleteReview_success() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.deleteReview("review1") } returns Unit

        //act
        val result = repository.deleteReview("review1")

        //assert
        assertThat(result.isSuccess).isTrue()
        coVerify { remoteDataSource.deleteReview("review1") }
    }
}
