package com.example.gamercornerapp.ui.Screens.login

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        userRepository = mockk()
        viewModel = LoginViewModel(authRepository, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login emptyFields showsError`() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")

        viewModel.onLoginClick()
        
        val state = viewModel.uiState.value
        assertTrue(state.showError)
        assertEquals(R.string.error_all_fields_required, state.errorRes)
    }

    @Test
    fun `login invalidEmail showsError`() = runTest {
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")

        viewModel.onLoginClick()

        val state = viewModel.uiState.value
        assertTrue(state.showError)
        assertEquals(R.string.error_invalid_email, state.errorRes)
    }

    @Test
    fun `login success navigatesToFeed`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        
        coEvery { authRepository.signIn(email, password) } returns Result.success(Unit)
        coEvery { userRepository.updateFCMToken(any()) } returns Result.success(Unit)
        
        viewModel.onLoginClick()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.showError)
        assertTrue(state.navigateToFeed)
    }
}
