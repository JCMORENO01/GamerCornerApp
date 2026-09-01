package com.example.gamercornerapp.ui.Screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.register.components.AlreadyAccountSection
import com.example.gamercornerapp.ui.Screens.register.components.RegisterForm
import com.example.gamercornerapp.ui.Screens.register.components.RegisterHeader
import com.example.gamercornerapp.ui.Screens.register.components.TermsSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateToFeed) {
        if (uiState.navigateToFeed) {
            onRegisterClick()
            viewModel.onNavigationHandled()
        }
    }

    RegisterScreenContent(
        fullName = uiState.fullName,
        username = uiState.username,
        email = uiState.email,
        password = uiState.password,
        confirmPassword = uiState.confirmPassword,
        birthDate = uiState.birthDate,
        showPassword = uiState.showPassword,
        showConfirmPassword = uiState.showConfirmPassword,
        termsAccepted = uiState.termsAccepted,
        showError = uiState.showError,
        errorMessage = uiState.errorRes?.let { stringResource(id = it) } ?: "",

        onFullNameChange = viewModel::onFullNameChange,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onBirthDateChange = viewModel::onBirthDateChange,
        onShowPasswordChange = viewModel::togglePasswordVisibility,
        onShowConfirmPasswordChange = viewModel::toggleConfirmPasswordVisibility,
        onTermsChange = viewModel::onTermsChange,

        onRegisterClick = viewModel::onRegisterClick,
        onLoginClick = onLoginClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}


@Composable
fun RegisterScreenContent(
    fullName: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    birthDate: String,

    showPassword: Boolean,
    showConfirmPassword: Boolean,

    termsAccepted: Boolean,

    showError: Boolean = false,
    errorMessage: String = "",

    onFullNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,

    onShowPasswordChange: () -> Unit,
    onShowConfirmPasswordChange: () -> Unit,

    onTermsChange: (Boolean) -> Unit,

    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            RegisterHeader(
                onBackClick = onBackClick
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }


            RegisterForm(
                fullName = fullName,
                username = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                birthDate = birthDate,

                showPassword = showPassword,
                showConfirmPassword = showConfirmPassword,

                onFullNameChange = onFullNameChange,
                onUsernameChange = onUsernameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                onBirthDateChange = onBirthDateChange,

                onShowPasswordChange = onShowPasswordChange,
                onShowConfirmPasswordChange = onShowConfirmPasswordChange
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            TermsSection(
                checked = termsAccepted,
                onCheckedChange = onTermsChange
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            AppButton(
                text = stringResource(
                    id = R.string.btn_action_create_account
                ),

                onClick = onRegisterClick
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            AlreadyAccountSection(
                onLoginClick = onLoginClick
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Register Dark"
)
@Composable
fun RegisterScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        RegisterScreen(
            onRegisterClick = { },
            onLoginClick = { },
            onBackClick = { }
        )
    }
}