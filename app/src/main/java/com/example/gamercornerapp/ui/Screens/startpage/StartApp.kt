package com.example.gamercornerapp.ui.Screens.startpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
    modifier: Modifier = Modifier
) {

    StartAppContent(
        modifier = modifier
    )
}


@Composable
fun StartAppContent(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
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


            // Boton continuar reutilizando AppButton
            AppButton(
                text = stringResource(
                    id = R.string.btn_continue
                ) + " ›",
                onClick = { }
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StartAppPreview() {

    GamerCornerAppTheme {
        StartApp()
    }
}