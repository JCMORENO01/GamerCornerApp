package com.example.gamercornerapp.ui.Screens.login

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.ui.Screens.login.components.CreateAccountSection
import com.example.gamercornerapp.ui.Screens.login.components.GoogleLoginButton
import com.example.gamercornerapp.ui.Screens.login.components.LoginDivider
import com.example.gamercornerapp.ui.Screens.login.components.LoginForm
import com.example.gamercornerapp.ui.Screens.login.components.LoginHeader
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {

    // Estado del correo
    var email by remember {
        mutableStateOf("")
    }


    // Estado de la contraseña
    var password by remember {
        mutableStateOf("")
    }


    // Estado para mostrar u ocultar la contraseña
    var showPassword by remember {
        mutableStateOf(false)
    }


    LoginScreenContent(
        email = email,
        password = password,
        showPassword = showPassword,

        onEmailChange = {
            email = it
        },

        onPasswordChange = {
            password = it
        },

        onShowPasswordChange = {
            showPassword = !showPassword
        },

        onLoginClick = { },

        modifier = modifier
    )
}


@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    showPassword: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowPasswordChange: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginHeader()


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            LoginForm(
                email = email,
                password = password,
                showPassword = showPassword,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onShowPasswordChange = onShowPasswordChange,
                onLoginClick = onLoginClick
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


            CreateAccountSection()
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

        LoginScreen()
    }
}