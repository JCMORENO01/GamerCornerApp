package com.example.gamercornerapp.ui.Screens.startpage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun PageIndicators(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Primer indicador activo
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    colorResource(
                        id = R.color.brand_primary
                    ),
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
                    colorResource(
                        id = R.color.card_background
                    ),
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
                    colorResource(
                        id = R.color.card_background
                    ),
                    CircleShape
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PageIndicatorsPreview() {

    GamerCornerAppTheme {

        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(28.dp)
        ) {

            PageIndicators()
        }
    }
}