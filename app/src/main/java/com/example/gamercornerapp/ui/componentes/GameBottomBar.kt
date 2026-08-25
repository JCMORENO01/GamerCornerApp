package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gamercornerapp.R
import com.example.gamercornerapp.navigation.Screen


private data class BottomNavItem(
    val iconRes: Int,
    val label: String,
    val route: String
)


@Composable
fun GamerBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()


    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route


    val homeItem = BottomNavItem(
        iconRes = R.drawable.home_button,
        label = stringResource(
            id = R.string.nav_home
        ),
        route = Screen.Feed.route
    )


    val exploreItem = BottomNavItem(
        iconRes = R.drawable.explore_button,
        label = stringResource(
            id = R.string.nav_explore
        ),
        route = Screen.Explore.route
    )


    val notificationsItem = BottomNavItem(
        iconRes = R.drawable.campana_button,
        label = "Comunidad",
        route = Screen.Notifications.route
    )


    val profileItem = BottomNavItem(
        iconRes = R.drawable.people_button,
        label = stringResource(
            id = R.string.nav_profile
        ),
        route = Screen.SelfProfile.route
    )


    // Gradiente del botón central
    val gradientAdd = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )


    // Color de la línea superior
    val lineColor =
        MaterialTheme.colorScheme.onSurfaceVariant


    Box(
        modifier = modifier
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

                    val strokeWidth =
                        2.dp.toPx()

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


            // HOME
            BottomNavButton(
                item = homeItem,

                isSelected =
                    currentRoute == homeItem.route,

                modifier = Modifier.weight(1f),

                onClick = {

                    navController.navigate(
                        homeItem.route
                    )
                }
            )


            // EXPLORAR
            BottomNavButton(
                item = exploreItem,

                isSelected =
                    currentRoute == exploreItem.route,

                modifier = Modifier.weight(1f),

                onClick = {

                    navController.navigate(
                        exploreItem.route
                    )
                }
            )


            // ESPACIO PARA BOTÓN CENTRAL
            Spacer(
                modifier = Modifier.weight(1.2f)
            )


            // COMUNIDAD
            BottomNavButton(
                item = notificationsItem,

                isSelected =
                    currentRoute == notificationsItem.route,

                modifier = Modifier.weight(1f),

                onClick = {

                    navController.navigate(
                        notificationsItem.route
                    )
                }
            )


            // PERFIL
            BottomNavButton(
                item = profileItem,

                isSelected =
                    currentRoute == profileItem.route,

                modifier = Modifier.weight(1f),

                onClick = {

                    navController.navigate(
                        profileItem.route
                    )
                }
            )
        }


        // BOTÓN CENTRAL FLOTANTE
        Box(
            modifier = Modifier
                .size(56.dp)
                .offset(
                    y = (-10).dp
                )
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    spotColor =
                        MaterialTheme.colorScheme.primary
                )
                .border(
                    width = 2.dp,
                    color =
                        MaterialTheme.colorScheme.background,
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

                color =
                    MaterialTheme.colorScheme.onPrimary,

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
private fun BottomNavButton(
    item: BottomNavItem,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val tintColor =
        if (isSelected) {

            MaterialTheme.colorScheme.primary

        } else {

            MaterialTheme.colorScheme.onSurfaceVariant
        }


    Column(
        modifier = modifier
            .clickable {
                onClick()
            },

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            androidx.compose.foundation.layout.Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = item.iconRes
            ),

            contentDescription =
                item.label,

            modifier =
                Modifier.size(50.dp),

            colorFilter =
                ColorFilter.tint(
                    tintColor
                )
        )


        Text(
            text = item.label,

            color = tintColor,

            fontSize = 11.sp,

            fontWeight =
                if (isSelected) {

                    FontWeight.SemiBold

                } else {

                    FontWeight.Normal
                },

            textAlign =
                TextAlign.Center,

            maxLines = 1,

            modifier =
                Modifier.offset(
                    y = (-12).dp
                )
        )
    }
}