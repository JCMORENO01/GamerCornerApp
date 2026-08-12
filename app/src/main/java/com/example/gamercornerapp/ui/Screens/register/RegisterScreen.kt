package com.example.gamercornerapp.ui.Screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {

    RegisterScreenContent(
        modifier = modifier
    )
}


@Composable
fun RegisterScreenContent(
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

            // Boton de retroceso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = stringResource(
                        id = R.string.back_arrow
                    ),
                    color = colorResource(
                        id = R.color.white
                    ),
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
                color = colorResource(
                    id = R.color.white
                ),
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
                color = colorResource(
                    id = R.color.text_secondary
                ),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )


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

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // Terminos y condiciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = false,
                    onCheckedChange = { },
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
                    text = stringResource(
                        id = R.string.terms_complete_text
                    ),
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