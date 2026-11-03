package com.example.gamercornerapp.ui.Screens.register

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
class RegisterViewModelTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: RegisterViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        userRepository = mockk()
        viewModel = RegisterViewModel(authRepository, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `register emptyFields showsError`() = runTest {
        viewModel.onRegisterClick()

        val state = viewModel.uiState.value
        assertTrue(state.showError)
        assertEquals(R.string.error_all_fields_required, state.errorRes)
    }

    @Test
    fun `register shortPassword showsError`() = runTest {
        viewModel.onEmailChange("test@test.com")
        viewModel.onFullNameChange("Test User")
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("123")
        viewModel.onConfirmPasswordChange("123")
        viewModel.onTermsChange(true)

        viewModel.onRegisterClick()

        val state = viewModel.uiState.value
        assertTrue(state.showError)
        assertEquals(R.string.error_password_too_short, state.errorRes)
    }

    @Test
    fun `register passwordsDoNotMatch showsError`() = runTest {
        viewModel.onEmailChange("test@test.com")
        viewModel.onFullNameChange("Test User")
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("1234567")
        viewModel.onConfirmPasswordChange("7654321")
        viewModel.onTermsChange(true)

        viewModel.onRegisterClick()

        val state = viewModel.uiState.value
        assertTrue(state.showError)
        assertEquals(R.string.error_generic_auth, state.errorRes)
    }
}
