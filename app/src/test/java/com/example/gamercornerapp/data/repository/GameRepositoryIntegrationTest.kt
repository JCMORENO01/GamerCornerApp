package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.util.FakerDataGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameRepositoryIntegrationTest {

    private lateinit var remoteDataSource: GameRemoteDataSource
    private lateinit var repository: GameRepository

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        repository = GameRepository(remoteDataSource)
    }

    @After
    fun tearDown() {
        io.mockk.clearAllMocks()
    }

    @Test
    fun getGames_success_returnsMappedGames() = runTest {
        val fakeDtos = listOf(FakerDataGenerator.generateFakeGame("game_1"))
        coEvery { remoteDataSource.getGames() } returns fakeDtos

        val result = repository.getGames()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals(fakeDtos[0].title, result.getOrNull()?.get(0)?.title)
    }

    @Test
    fun getGames_datasourceException_returnsFailureResult() = runTest {
        coEvery { remoteDataSource.getGames() } throws RuntimeException("Network error")

        val result = repository.getGames()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun getGameById_success_returnsMappedGame() = runTest {
        val fakeDto = FakerDataGenerator.generateFakeGame("game_2")
        coEvery { remoteDataSource.getGameById("game_2") } returns fakeDto

        val result = repository.getGameById("game_2")

        assertTrue(result.isSuccess)
        assertEquals(fakeDto.id, result.getOrNull()?.id)
        assertEquals(fakeDto.title, result.getOrNull()?.title)
    }

    @Test
    fun getGameById_notFound_returnsFailureResult() = runTest {
        coEvery { remoteDataSource.getGameById(any()) } throws RuntimeException("Game not found")

        val result = repository.getGameById("invalid_id")

        assertTrue(result.isFailure)
        assertEquals("Game not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun getGames_emptyList_returnsSuccessEmpty() = runTest {
        coEvery { remoteDataSource.getGames() } returns emptyList()

        val result = repository.getGames()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun getGameById_nullOrEmptyId_returnsFailure() = runTest {
        coEvery { remoteDataSource.getGameById("") } throws IllegalArgumentException("Invalid ID")

        val result = repository.getGameById("")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun getGames_timeoutException_returnsFailure() = runTest {
        coEvery { remoteDataSource.getGames() } throws java.util.concurrent.TimeoutException("Connection timeout")

        val result = repository.getGames()

        assertTrue(result.isFailure)
        assertEquals("Connection timeout", result.exceptionOrNull()?.message)
    }

    @Test
    fun getGameById_serverError_returnsFailure() = runTest {
        coEvery { remoteDataSource.getGameById(any()) } throws RuntimeException("500 Internal Server Error")

        val result = repository.getGameById("game_500")

        assertTrue(result.isFailure)
        assertEquals("500 Internal Server Error", result.exceptionOrNull()?.message)
    }
}
