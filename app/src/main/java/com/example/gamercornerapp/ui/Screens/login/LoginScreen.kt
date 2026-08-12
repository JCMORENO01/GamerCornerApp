package com.example.gamercornerapp.ui.Screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    LoginScreenContent(
        modifier = modifier
    )
}


@Composable
fun LoginScreenContent(modifier: Modifier = Modifier) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo
            Image(
                painter = painterResource(
                    id = R.drawable.logo_gamer
                ),
                contentDescription = "Gamer's Corner Logo",
                modifier = Modifier
                    .width(500.dp)
                    .height(200.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // Titulo
            Text(
                text = stringResource(id = R.string.welcome_back),
                color = colorResource(id = R.color.white),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // Subtitulo
            Text(
                text = stringResource(id = R.string.login_subtitle),
                color = colorResource(id = R.color.text_secondary),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // Campo de correo
            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(
                    id = R.string.email_or_user_hint
                ),
                placeholder = "ejemplo@gamer.com"
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Campo de contraseña
            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(
                    id = R.string.password_hint
                ),
                placeholder = "Ingresa tu contraseña",
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
                    color = colorResource(
                        id = R.color.brand_primary
                    ),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // Boton de iniciar sesion
            AppButton(
                text = stringResource(
                    id = R.string.login_button
                ),
                onClick = { }
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // Separador
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = colorResource(
                        id = R.color.card_background
                    )
                )

                Text(
                    text = " o ",
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        horizontal = 8.dp
                    )
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = colorResource(
                        id = R.color.card_background
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // Boton de Google
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(
                        id = R.color.white
                    ),
                    containerColor = colorResource(
                        id = R.color.card_background
                    )
                ),
                border = null
            ) {

                Text(
                    text = stringResource(
                        id = R.string.google_login
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )


            // Crear cuenta
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(
                        id = R.string.no_account
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 13.sp
                )

                Text(
                    text = stringResource(
                        id = R.string.create_account
                    ),
                    color = colorResource(
                        id = R.color.brand_primary
                    ),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    GamerCornerAppTheme {
        LoginScreen()
    }
}