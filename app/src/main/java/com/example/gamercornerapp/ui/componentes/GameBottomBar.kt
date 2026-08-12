package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R


@Composable
fun GamerBottomBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .background(
                colorResource(id = R.color.brand_background)
            )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1. Inicio
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.home_button),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.text_secondary))
                    )
                    Text(
                        text = stringResource(id = R.string.nav_home),
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }


            // 2. Explorar
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.explore_button),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.text_secondary))
                    )
                    Text(
                        text = stringResource(id = R.string.nav_explore),
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }


            // 3. Espacio central (Hueco para el botón +)
            // Usamos un weight ligeramente mayor para dar aire al botón central
            Spacer(modifier = Modifier.weight(1.1f))


            // 4. Notificaciones
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.campana_button),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.brand_primary))
                    )
                    Text(
                        text = stringResource(id = R.string.nav_notifications),
                        color = colorResource(id = R.color.brand_primary),
                        fontSize = 10.sp, // Un poco más pequeño para que quepa "Notificaciones"
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        fontWeight = FontWeight.Medium
                    )
                }
            }


            // 5. Perfil
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.people_button),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.text_secondary))
                    )
                    Text(
                        text = stringResource(id = R.string.nav_profile),
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }


        // Gradiente y diseño del FAB
        val gradientAdd = Brush.verticalGradient(
            colors = listOf(
                colorResource(id = R.color.brand_primary),
                colorResource(id = R.color.brand_purple)
            )
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-15).dp)
                .background(
                    gradientAdd,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.plus_sign),
                color = colorResource(id = R.color.white),
                fontSize = 38.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}