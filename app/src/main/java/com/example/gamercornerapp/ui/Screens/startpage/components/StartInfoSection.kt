package com.example.gamercornerapp.ui.Screens.startpage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun StartInfoSection(
    modifier: Modifier = Modifier
) {

    val isDark = isSystemInDarkTheme()
    val logoRes = if (isDark) {
        R.drawable.logo_gamer1      // Blanco para modo oscuro
    } else {
        R.drawable.logo_fondo_claro // Negro para modo claro
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo
        Image(
            painter = painterResource(
                id = logoRes
            ),
            contentDescription = stringResource(
                id = R.string.logo_content_description
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Frase principal
        Text(
            text = stringResource(
                id = R.string.start_title
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Descripcion
        Text(
            text = stringResource(
                id = R.string.start_description
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}


@Preview(
    showBackground = true,
    name = "Start Info Dark"
)
@Composable
fun StartInfoSectionPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(28.dp)
        ) {

            StartInfoSection()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Start Info Light"
)
@Composable
fun StartInfoSectionLightPreview() {

    GamerCornerAppTheme(
        darkTheme = false
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(28.dp)
        ) {

            StartInfoSection()
        }
    }
}