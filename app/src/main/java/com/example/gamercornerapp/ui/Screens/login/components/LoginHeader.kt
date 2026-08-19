package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo
        Image(
            painter = painterResource(
                id = R.drawable.logo_gamer
            ),
            contentDescription = stringResource(
                id = R.string.logo_content_description
            ),
            modifier = Modifier
                .width(500.dp)
                .height(200.dp)
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Titulo
        Text(
            text = stringResource(
                id = R.string.welcome_back
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(6.dp)
        )


        // Subtitulo
        Text(
            text = stringResource(
                id = R.string.login_subtitle
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginHeaderPreview() {

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

            LoginHeader()
        }
    }
}