package com.example.gamercornerapp.ui.Screens.startpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.startpage.components.PageIndicators
import com.example.gamercornerapp.ui.Screens.startpage.components.StartInfoSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun StartApp(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    StartAppContent(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick,
        modifier = modifier
    )
}


@Composable
fun StartAppContent(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(
                horizontal = 28.dp,
                vertical = 28.dp
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(80.dp)
            )


            // Informacion principal
            StartInfoSection()


            Spacer(
                modifier = Modifier.weight(1f)
            )


            // Indicadores
            PageIndicators()


            Spacer(
                modifier = Modifier.height(48.dp)
            )


            // Boton Iniciar Sesion
            AppButton(
                text = stringResource(
                    id = R.string.login_button
                ),

                onClick = onLoginClick
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // Boton Registrarse
            AppButton(
                text = stringResource(
                    id = R.string.btn_action_create_account
                ),

                onClick = onRegisterClick
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Start App Dark"
)
@Composable
fun StartAppPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        StartApp(
            onLoginClick = { },
            onRegisterClick = { }
        )
    }
}