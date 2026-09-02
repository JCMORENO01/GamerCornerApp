package com.example.gamercornerapp.ui.Screens.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamercornerapp.ui.Screens.login.components.AnimatedSplashScreen
import com.example.gamercornerapp.ui.Screens.login.components.CreateAccountSection
import com.example.gamercornerapp.ui.Screens.login.components.GoogleLoginButton
import com.example.gamercornerapp.ui.Screens.login.components.LoginDivider
import com.example.gamercornerapp.ui.Screens.login.components.LoginForm
import com.example.gamercornerapp.ui.Screens.login.components.LoginHeader
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateToFeed) {
        if (uiState.navigateToFeed) {
            onLoginClick()
            viewModel.onNavigationHandled()
        }
    }

    LoginScreenContent(
        email = uiState.email,
        password = uiState.password,
        showPassword = uiState.showPassword,
        isLogoUp = uiState.isLogoUp,
        showError = uiState.showError,
        errorMessage = uiState.errorRes?.let { stringResource(id = it) } ?: "",

        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onShowPasswordChange = viewModel::togglePasswordVisibility,
        onAnimationFinished = viewModel::onAnimationFinished,

        onLoginClick = viewModel::onLoginClick,
        onCreateAccountClick = onCreateAccountClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onBackClick = onBackClick,

        modifier = modifier
    )
}


@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    showPassword: Boolean,
    isLogoUp: Boolean,
    showError: Boolean = false,
    errorMessage: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowPasswordChange: () -> Unit,
    onAnimationFinished: () -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Opacidad del formulario
    val formAlpha by animateFloatAsState(
        targetValue = if (isLogoUp) 1f else 0f,

        animationSpec = tween(
            durationMillis = 500
        ),

        label = "FormAlphaAnimation"
    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        if (!isLogoUp) {
            AnimatedSplashScreen(
                onAnimationFinished = onAnimationFinished
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer {
                    alpha = formAlpha
                }
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

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                LoginHeader(
                    onBackClick = onBackClick
                )


                Spacer(
                    modifier = Modifier.height(28.dp)
                )


                LoginForm(
                    email = email,
                    password = password,
                    showPassword = showPassword,
                    showError = showError,
                    errorMessage = errorMessage,

                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onShowPasswordChange = onShowPasswordChange,

                    onLoginClick = onLoginClick,
                    onForgotPasswordClick = onForgotPasswordClick
                )


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                LoginDivider()


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                GoogleLoginButton()


                Spacer(
                    modifier = Modifier.height(32.dp)
                )


                CreateAccountSection(
                    onCreateAccountClick = onCreateAccountClick
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Login Dark"
)
@Composable
fun LoginScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        LoginScreen(
            onLoginClick = { },
            onCreateAccountClick = { },
            onForgotPasswordClick = { },
            onBackClick = { }
        )
    }
}

@Preview(
    showBackground = true,
    name = "Login Light"
)
@Composable
fun LoginScreenLightPreview() {

    GamerCornerAppTheme(
        darkTheme = false
    ) {

        LoginScreen(
            onLoginClick = { },
            onCreateAccountClick = { },
            onForgotPasswordClick = { },
            onBackClick = { }
        )
    }
}