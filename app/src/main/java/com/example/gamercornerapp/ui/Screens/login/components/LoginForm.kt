package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.componentes.AppPasswordField
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginForm(
    email: String,
    password: String,
    showPassword: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowPasswordChange: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        // Campo de correo
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(
                id = R.string.email_or_user_hint
            ),
            placeholder = stringResource(
                id = R.string.hint_email
            )
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // Campo de contraseña
        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(
                id = R.string.password_hint
            ),
            placeholder = stringResource(
                id = R.string.login_password_placeholder
            ),
            showPassword = showPassword,
            onShowPasswordChange = onShowPasswordChange
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // Olvide mi contraseña
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(
                    id = R.string.forgot_password
                ),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // Boton iniciar sesion
        AppButton(
            text = stringResource(
                id = R.string.login_button
            ),
            onClick = onLoginClick
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(24.dp)
        ) {

            LoginForm(
                email = "juan@gmail.com",
                password = "123456",
                showPassword = false,
                onEmailChange = { },
                onPasswordChange = { },
                onShowPasswordChange = { },
                onLoginClick = { }
            )
        }
    }
}