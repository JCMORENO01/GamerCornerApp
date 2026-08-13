package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RegisterHeader(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
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
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterHeaderPreview() {

    GamerCornerAppTheme {

        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            RegisterHeader()
        }
    }
}