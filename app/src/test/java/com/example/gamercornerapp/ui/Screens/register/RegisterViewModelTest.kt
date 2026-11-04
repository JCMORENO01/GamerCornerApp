package com.example.gamercornerapp.ui.Screens.register

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
    fun register_emptyFields_showsError() = runTest {
        //AAA
        //arrange
        //act
        viewModel.onRegisterClick()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_all_fields_required)
    }

    @Test
    fun register_shortPassword_showsError() = runTest {
        //AAA
        //arrange
        viewModel.onEmailChange("test@test.com")
        viewModel.onFullNameChange("Test User")
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("123")
        viewModel.onConfirmPasswordChange("123")
        viewModel.onTermsChange(true)

        //act
        viewModel.onRegisterClick()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_password_too_short)
    }

    @Test
    fun register_passwordsDoNotMatch_showsError() = runTest {
        //AAA
        //arrange
        viewModel.onEmailChange("test@test.com")
        viewModel.onFullNameChange("Test User")
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("1234567")
        viewModel.onConfirmPasswordChange("7654321")
        viewModel.onTermsChange(true)

        //act
        viewModel.onRegisterClick()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isTrue()
        assertThat(state.errorRes).isEqualTo(R.string.error_generic_auth)
    }

    @Test
    fun register_success_navigatesToFeed() = runTest {
        //AAA
        //arrange
        val email = "valid@test.com"
        val password = "password123"
        val mockUser = mockk<com.google.firebase.auth.FirebaseUser>()
        every { mockUser.uid } returns "test_reg_uid"
        every { authRepository.currentUser } returns mockUser

        viewModel.onEmailChange(email)
        viewModel.onFullNameChange("Valid User")
        viewModel.onUsernameChange("validuser")
        viewModel.onPasswordChange(password)
        viewModel.onConfirmPasswordChange(password)
        viewModel.onTermsChange(true)

        coEvery { authRepository.signUp(email, password) } returns Result.success(Unit)
        coEvery { userRepository.createUserProfile(any(), any(), any(), any(), any()) } returns Result.success(Unit)
        coEvery { userRepository.updateFCMToken(any()) } returns Result.success(Unit)

        //act
        viewModel.onRegisterClick()
        advanceUntilIdle()

        //assert
        val state = viewModel.uiState.value
        assertThat(state.showError).isFalse()
        assertThat(state.navigateToFeed).isTrue()
    }
}
