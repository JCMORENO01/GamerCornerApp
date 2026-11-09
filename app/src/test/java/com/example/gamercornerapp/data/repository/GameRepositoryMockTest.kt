package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.util.FakerDataGenerator
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameRepositoryMockTest {

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
        //AAA
        //arrange
        val fakeDtos = listOf(FakerDataGenerator.generateFakeGame("game_1"))
        coEvery { remoteDataSource.getGames() } returns fakeDtos

        //act
        val result = repository.getGames()

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.size).isEqualTo(1)
        assertThat(result.getOrNull()?.get(0)?.title).isEqualTo(fakeDtos[0].title)
    }

    @Test
    fun getGames_datasourceException_returnsFailureResult() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGames() } throws RuntimeException("Network error")

        //act
        val result = repository.getGames()

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Network error")
    }

    @Test
    fun getGameById_success_returnsMappedGame() = runTest {
        //AAA
        //arrange
        val fakeDto = FakerDataGenerator.generateFakeGame("game_2")
        coEvery { remoteDataSource.getGameById("game_2") } returns fakeDto

        //act
        val result = repository.getGameById("game_2")

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.id).isEqualTo(fakeDto.id)
        assertThat(result.getOrNull()?.title).isEqualTo(fakeDto.title)
    }

    @Test
    fun getGameById_notFound_returnsFailureResult() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGameById(any()) } throws RuntimeException("Game not found")

        //act
        val result = repository.getGameById("invalid_id")

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Game not found")
    }

    @Test
    fun getGames_emptyList_returnsSuccessEmpty() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGames() } returns emptyList()

        //act
        val result = repository.getGames()

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun getGameById_nullOrEmptyId_returnsFailure() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGameById("") } throws IllegalArgumentException("Invalid ID")

        //act
        val result = repository.getGameById("")

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun getGames_timeoutException_returnsFailure() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGames() } throws java.util.concurrent.TimeoutException("Connection timeout")

        //act
        val result = repository.getGames()

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Connection timeout")
    }

    @Test
    fun getGameById_serverError_returnsFailure() = runTest {
        //AAA
        //arrange
        coEvery { remoteDataSource.getGameById(any()) } throws RuntimeException("500 Internal Server Error")

        //act
        val result = repository.getGameById("game_500")

        //assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("500 Internal Server Error")
    }
}
