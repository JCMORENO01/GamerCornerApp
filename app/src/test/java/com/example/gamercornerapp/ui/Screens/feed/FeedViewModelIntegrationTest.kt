package com.example.gamercornerapp.ui.Screens.feed

import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import com.example.gamercornerapp.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
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
    fun loadFeed_updatesStateWithPosts() = runTest {
        //AAA
        //arrange
        val dummyPost = mockk<FeedPost>(relaxed = true)
        every { dummyPost.id } returns "post1"
        every { reviewRepository.listenAllReviewsAsFeed() } returns flowOf(listOf(dummyPost))

        //act
        viewModel.loadFeed()
        advanceUntilIdle()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.posts.size).isEqualTo(1)
        assertThat(state.posts[0].id).isEqualTo("post1")
    }

    @Test
    fun onTabSelected_updatesSelectedTabIndex() = runTest {
        //AAA
        //arrange
        val dummyPost = mockk<FeedPost>(relaxed = true)
        every { dummyPost.id } returns "post1"
        every { reviewRepository.listenAllReviewsAsFeed() } returns flowOf(listOf(dummyPost))
        every { authRepository.currentUser } returns null

        //act
        viewModel.loadFeed()
        viewModel.onTabSelected(1)
        advanceUntilIdle()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.selectedTabIndex).isEqualTo(1)
    }
}
