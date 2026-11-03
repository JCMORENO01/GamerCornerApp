package com.example.gamercornerapp.ui.Screens.feed

import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelIntegrationTest {

    private lateinit var reviewRepository: ReviewRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: FeedViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        reviewRepository = mockk()
        authRepository = mockk()
        userRepository = mockk()
        viewModel = FeedViewModel(reviewRepository, authRepository, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFeed updatesStateWithPosts`() = runTest {
        val dummyPost = mockk<FeedPost>(relaxed = true)
        every { dummyPost.id } returns "post1"
        
        every { reviewRepository.listenAllReviewsAsFeed() } returns flowOf(listOf(dummyPost))
        
        viewModel.loadFeed()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.posts.size)
        assertEquals("post1", state.posts[0].id)
    }
}