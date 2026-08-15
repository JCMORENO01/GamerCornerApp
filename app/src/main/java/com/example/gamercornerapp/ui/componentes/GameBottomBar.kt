package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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
fun GamerBottomBar(
    selectedTab: Int = 3
) {
    // Gradiente en diagonal para el FAB flotante
    val gradientAdd = Brush.linearGradient(
        colors = listOf(
            colorResource(id = R.color.brand_primary),
            colorResource(id = R.color.brand_purple)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(colorResource(id = R.color.brand_background)),
        contentAlignment = Alignment.Center
    ) {
        val lineColor = colorResource(R.color.text_secondary)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, strokeWidth / 2),
                        end = Offset(size.width, strokeWidth / 2),
                        strokeWidth = strokeWidth
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Home
            BottomNavItem(
                iconRes = R.drawable.home_button,
                label = stringResource(id = R.string.nav_home), // "Home"
                isSelected = selectedTab == 0,
                modifier = Modifier.weight(1f)
            )

            // 2. Explorar
            BottomNavItem(
                iconRes = R.drawable.explore_button,
                label = stringResource(id = R.string.nav_explore), // "Explorar"
                isSelected = selectedTab == 1,
                modifier = Modifier.weight(1f)
            )

            // 3. Espacio central para el botón flotante (+)
            Spacer(modifier = Modifier.weight(1.2f))

            // 4. Comunidad
            BottomNavItem(
                iconRes = R.drawable.campana_button, // Cambiar por tu icono de comunidad si tienes uno específico
                label = "Comunidad",
                isSelected = selectedTab == 2,
                modifier = Modifier.weight(1f)
            )

            // 5. Perfil
            BottomNavItem(
                iconRes = R.drawable.people_button,
                label = stringResource(id = R.string.nav_profile), // "Perfil"
                isSelected = selectedTab == 3,
                modifier = Modifier.weight(1f)
            )
        }

        // Botón central flotante (+) con sombra y borde sutil
        Box(
            modifier = Modifier
                .size(56.dp)
                .offset(y = (-10).dp)
                .shadow(elevation = 12.dp, shape = CircleShape, spotColor = colorResource(id = R.color.brand_primary))
                .border(2.dp, colorResource(id = R.color.brand_background), CircleShape)
                .background(gradientAdd, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.plus_sign),
                color = colorResource(id = R.color.white),
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = (-2).dp)
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tintColor = colorResource(
        id = if (isSelected) R.color.brand_primary else R.color.text_secondary
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(50.dp),
            colorFilter = ColorFilter.tint(tintColor)
        )
        Text(
            text = label,
            color = tintColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.offset(y = (-12).dp) // <- Sube el texto hacia la imagen
        )
    }
}