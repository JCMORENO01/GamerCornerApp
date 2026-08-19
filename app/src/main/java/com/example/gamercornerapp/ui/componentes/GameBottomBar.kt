package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R


@Composable
fun GamerBottomBar(
    selectedTab: Int = 3,
    onTabSelected: (Int) -> Unit = {}
) {

    // Gradiente en diagonal para el boton flotante
    val gradientAdd = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )

    // Color de la linea superior
    val lineColor = MaterialTheme.colorScheme.onSurfaceVariant


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .background(
                MaterialTheme.colorScheme.background
            ),

        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {

                    val strokeWidth = 2.dp.toPx()

                    drawLine(
                        color = lineColor,
                        start = Offset(
                            0f,
                            strokeWidth / 2
                        ),
                        end = Offset(
                            size.width,
                            strokeWidth / 2
                        ),
                        strokeWidth = strokeWidth
                    )
                },

            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1. Home
            BottomNavItem(
                iconRes = R.drawable.home_button,
                label = stringResource(
                    id = R.string.nav_home
                ),
                isSelected = selectedTab == 0,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(0) }
            )


            // 2. Explorar
            BottomNavItem(
                iconRes = R.drawable.explore_button,
                label = stringResource(
                    id = R.string.nav_explore
                ),
                isSelected = selectedTab == 1,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(1) }
            )


            // 3. Espacio central para el boton flotante
            Spacer(
                modifier = Modifier.weight(1.2f)
            )


            // 4. Comunidad
            BottomNavItem(
                iconRes = R.drawable.campana_button,
                label = "Comunidad",
                isSelected = selectedTab == 2,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(2) }
            )


            // 5. Perfil
            BottomNavItem(
                iconRes = R.drawable.people_button,
                label = stringResource(
                    id = R.string.nav_profile
                ),
                isSelected = selectedTab == 3,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(3) }
            )
        }


        // Boton central flotante
        Box(
            modifier = Modifier
                .size(56.dp)
                .offset(y = (-10).dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.primary
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape
                )
                .background(
                    gradientAdd,
                    CircleShape
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(
                    id = R.string.plus_sign
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(
                    y = (-2).dp
                )
            )
        }
    }
}


@Composable
private fun BottomNavItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val tintColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }


    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = iconRes
            ),
            contentDescription = label,
            modifier = Modifier.size(50.dp),
            colorFilter = ColorFilter.tint(
                tintColor
            )
        )


        Text(
            text = label,
            color = tintColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.offset(
                y = (-12).dp
            )
        )
    }
}