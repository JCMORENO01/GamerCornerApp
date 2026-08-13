package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterForm(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        // Nombre completo
        AppTextField(
            value = "",
            onValueChange = { },
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
            value = "",
            onValueChange = { },
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
            value = "",
            onValueChange = { },
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
        AppTextField(
            value = "",
            onValueChange = { },
            label = stringResource(
                id = R.string.label_password
            ),
            placeholder = stringResource(
                id = R.string.hint_password
            )
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Confirmar contraseña
        AppTextField(
            value = "",
            onValueChange = { },
            label = stringResource(
                id = R.string.label_confirm_password
            ),
            placeholder = stringResource(
                id = R.string.hint_confirm_password
            )
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Fecha de nacimiento
        AppTextField(
            value = "",
            onValueChange = { },
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

    GamerCornerAppTheme {

        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            RegisterForm()
        }
    }
}