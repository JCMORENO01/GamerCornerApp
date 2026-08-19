package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppPasswordField
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterForm(
    fullName: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    birthDate: String,

    showPassword: Boolean,
    showConfirmPassword: Boolean,

    onFullNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,

    onShowPasswordChange: () -> Unit,
    onShowConfirmPasswordChange: () -> Unit,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        // Nombre completo
        AppTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = stringResource(
                id = R.string.label_fullname
            ),
            placeholder = stringResource(
                id = R.string.hint_fullname
            )
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Nombre de usuario
        AppTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = stringResource(
                id = R.string.label_username
            ),
            placeholder = stringResource(
                id = R.string.hint_username
            )
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Correo
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(
                id = R.string.label_email
            ),
            placeholder = stringResource(
                id = R.string.hint_email
            )
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Contraseña
        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(
                id = R.string.label_password
            ),
            placeholder = stringResource(
                id = R.string.hint_password
            ),
            showPassword = showPassword,
            onShowPasswordChange = onShowPasswordChange
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Confirmar contraseña
        AppPasswordField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(
                id = R.string.label_confirm_password
            ),
            placeholder = stringResource(
                id = R.string.hint_confirm_password
            ),
            showPassword = showConfirmPassword,
            onShowPasswordChange = onShowConfirmPasswordChange
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Fecha de nacimiento
        AppTextField(
            value = birthDate,
            onValueChange = onBirthDateChange,
            label = stringResource(
                id = R.string.label_birthdate
            ),
            placeholder = stringResource(
                id = R.string.hint_birthdate
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            RegisterForm(
                fullName = "Juan Romero",
                username = "juanromero",
                email = "juan@gmail.com",
                password = "123456",
                confirmPassword = "123456",
                birthDate = "10/05/2005",

                showPassword = false,
                showConfirmPassword = false,

                onFullNameChange = { },
                onUsernameChange = { },
                onEmailChange = { },
                onPasswordChange = { },
                onConfirmPasswordChange = { },
                onBirthDateChange = { },

                onShowPasswordChange = { },
                onShowConfirmPasswordChange = { }
            )
        }
    }
}