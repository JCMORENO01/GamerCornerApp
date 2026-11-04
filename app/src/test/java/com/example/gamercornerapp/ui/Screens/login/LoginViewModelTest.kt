package com.example.gamercornerapp.ui.Screens.login

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
    fun login_emptyFields_showsError() = runTest {
        //AAA
        //arrange
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")

        //act
        viewModel.onLoginClick()
        
        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_all_fields_required)
    }

    @Test
    fun login_invalidEmail_showsError() = runTest {
        //AAA
        //arrange
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")

        //act
        viewModel.onLoginClick()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_invalid_email)
    }

    @Test
    fun login_success_navigatesToFeed() = runTest {
        //AAA
        //arrange
        val email = "test@example.com"
        val password = "password123"
        val mockUser = mockk<com.google.firebase.auth.FirebaseUser>()
        every { mockUser.uid } returns "test_uid"
        every { mockUser.email } returns email
        every { mockUser.displayName } returns "Test User"
        every { authRepository.currentUser } returns mockUser
        
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        
        coEvery { authRepository.signIn(email, password) } returns Result.success(Unit)
        coEvery { userRepository.getUserById(any()) } returns Result.failure(Exception("Not found"))
        coEvery { userRepository.createUserProfile(any(), any(), any(), any(), any()) } returns Result.success(Unit)
        coEvery { userRepository.updateFCMToken(any()) } returns Result.success(Unit)
        
        //act
        viewModel.onLoginClick()
        advanceUntilIdle()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isFalse()
        assertThat(state.navigateToFeed).isTrue()
    }

    @Test
    fun login_failure_showsError() = runTest {
        //AAA
        //arrange
        val email = "test@example.com"
        val password = "wrongpassword"
        
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        
        coEvery { authRepository.signIn(email, password) } returns Result.failure(Exception("Credenciales inválidas"))
        
        //act
        viewModel.onLoginClick()
        advanceUntilIdle()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_invalid_credentials)
    }
}
