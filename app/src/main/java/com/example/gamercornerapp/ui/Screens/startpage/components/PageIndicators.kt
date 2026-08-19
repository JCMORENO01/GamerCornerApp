package com.example.gamercornerapp.ui.Screens.startpage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun PageIndicators(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Primer indicador activo
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    CircleShape
                )
        )


        Spacer(
            modifier = Modifier.width(14.dp)
        )


        // Segundo indicador
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                )
        )


        Spacer(
            modifier = Modifier.width(14.dp)
        )


        // Tercer indicador
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                )
        )
    }
}


@Preview(
    showBackground = true,
    name = "Page Indicators Dark"
)
@Composable
fun PageIndicatorsPreview() {

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

            PageIndicators()
        }
    }
}