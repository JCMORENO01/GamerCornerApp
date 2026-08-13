package com.example.gamercornerapp.ui.Screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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

            // Encabezado
            RegisterHeader()


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Formulario
            RegisterForm()


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // Terminos y condiciones
            TermsSection(
                checked = false
            )


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