package com.example.gamercornerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.componentes.AppButton
import com.example.gamercornerapp.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

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

            // Boton de retroceso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = "‹",
                    color = colorResource(id = R.color.white),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(2.dp)
            )


            // Titulo
            Text(
                text = stringResource(
                    id = R.string.title_create_account
                ),
                color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )


            // Subtitulo
            Text(
                text = stringResource(
                    id = R.string.subtitle_create_account
                ),
                color = colorResource(id = R.color.text_secondary),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Nombre completo
            AppTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = stringResource(
                    id = R.string.label_fullname
                ),
                placeholder = "Ej: Juan Pérez"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // Nombre de usuario
            AppTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(
                    id = R.string.label_username
                ),
                placeholder = "Ej: JuanP_21"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // Correo
            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(
                    id = R.string.label_email
                ),
                placeholder = "Ej: juanperez@gmail.com"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // Contraseña
            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(
                    id = R.string.label_password
                ),
                placeholder = "Mínimo 8 caracteres",
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // Confirmar contraseña
            AppTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(
                    id = R.string.label_confirm_password
                ),
                placeholder = "Repite tu contraseña",
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // Fecha de nacimiento
            AppTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Fecha de nacimiento",
                placeholder = "Selecciona tu fecha"
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // Terminos y condiciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = {
                        termsAccepted = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(
                            id = R.color.brand_primary
                        ),
                        uncheckedColor = colorResource(
                            id = R.color.text_secondary
                        ),
                        checkmarkColor = colorResource(
                            id = R.color.white
                        )
                    ),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Acepto los Términos y Condiciones y la Política de Privacidad",
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 10.sp
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Crear cuenta
            AppButton(
                text = stringResource(
                    id = R.string.btn_action_create_account
                ),
                onClick = { }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Ya tengo una cuenta
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(
                        id = R.string.has_account
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 12.sp
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.login_link
                    ),
                    color = colorResource(
                        id = R.color.brand_primary
                    ),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

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