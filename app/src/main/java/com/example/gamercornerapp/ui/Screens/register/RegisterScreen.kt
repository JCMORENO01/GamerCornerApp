package com.example.gamercornerapp.ui.Screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.register.components.AlreadyAccountSection
import com.example.gamercornerapp.ui.Screens.register.components.RegisterForm
import com.example.gamercornerapp.ui.Screens.register.components.RegisterHeader
import com.example.gamercornerapp.ui.Screens.register.components.TermsSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {

    // Estados del formulario
    var fullName by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var birthDate by remember {
        mutableStateOf("")
    }


    // Estados para mostrar las contraseñas
    var showPassword by remember {
        mutableStateOf(false)
    }

    var showConfirmPassword by remember {
        mutableStateOf(false)
    }


    // Estado de terminos y condiciones
    var termsAccepted by remember {
        mutableStateOf(false)
    }


    RegisterScreenContent(
        fullName = fullName,
        username = username,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        birthDate = birthDate,

        showPassword = showPassword,
        showConfirmPassword = showConfirmPassword,

        termsAccepted = termsAccepted,

        onFullNameChange = {
            fullName = it
        },

        onUsernameChange = {
            username = it
        },

        onEmailChange = {
            email = it
        },

        onPasswordChange = {
            password = it
        },

        onConfirmPasswordChange = {
            confirmPassword = it
        },

        onBirthDateChange = {
            birthDate = it
        },

        onShowPasswordChange = {
            showPassword = !showPassword
        },

        onShowConfirmPasswordChange = {
            showConfirmPassword = !showConfirmPassword
        },

        onTermsChange = {
            termsAccepted = it
        },

        onRegisterClick = { },

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

    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Encabezado
            RegisterHeader()


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Formulario
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


            // Terminos y condiciones
            TermsSection(
                checked = termsAccepted,
                onCheckedChange = onTermsChange
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Crear cuenta
            AppButton(
                text = stringResource(
                    id = R.string.btn_action_create_account
                ),
                onClick = onRegisterClick
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Ya tengo una cuenta
            AlreadyAccountSection()


            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {

    GamerCornerAppTheme {
        RegisterScreen()
    }
}