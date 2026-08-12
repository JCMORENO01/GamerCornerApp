package com.example.gamercornerapp.ui

import com.example.gamercornerapp.componentes.GamerBottomBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Titulo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {

            Text(
                text = "‹",
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 12.dp)
            )

            Text(
                text = stringResource(id = R.string.title_followers),
                color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        // Botones de seguidores y siguiendo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    colorResource(id = R.color.card_background),
                    RoundedCornerShape(24.dp)
                )
                .padding(4.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(
                        colorResource(id = R.color.brand_background)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = stringResource(id = R.string.tab_followers),
                    color = colorResource(id = R.color.brand_primary),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = stringResource(id = R.string.tab_following),
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Usuarios

        UserRow(
            name = "Drakool",
            handle = "@drakool_gamer",
            image = R.drawable.messi1,
            isFollowing = false
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        UserRow(
            name = "LadyAki",
            handle = "@ladyaki_x",
            image = R.drawable.messi1,
            isFollowing = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        UserRow(
            name = "ShadowX",
            handle = "@shadowx_99",
            image = R.drawable.messi2,
            isFollowing = false
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        UserRow(
            name = "NeoGamer",
            handle = "@neogamer_07",
            image = R.drawable.messi3,
            isFollowing = false
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        UserRow(
            name = "PixelPro",
            handle = "@pixelpro_gaming",
            image = R.drawable.messi4,
            isFollowing = false
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        UserRow(
            name = "RogueMaster",
            handle = "@roguemaster",
            image = R.drawable.messi1,
            isFollowing = false
        )


        // Espacio para mandar la barra inferior hacia abajo
        Spacer(
            modifier = Modifier.weight(1f)
        )


        GamerBottomBar()
    }
}


@Composable
fun UserRow(
    name: String,
    handle: String,
    image: Int,
    isFollowing: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Imagen y informacion del usuario
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = image),
                contentDescription = "Foto de $name",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = name,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = handle,
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 14.sp
                )
            }
        }


        // Boton seguir
        Box(
            modifier = Modifier
                .background(
                    color = if (isFollowing) {
                        colorResource(id = R.color.card_background)
                    } else {
                        colorResource(id = R.color.brand_primary)
                    },
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = if (isFollowing) {
                    "Siguiendo"
                } else {
                    "Seguir"
                },
                color = colorResource(id = R.color.white),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FollowersScreenPreview() {

    GamerCornerAppTheme {
        FollowersScreen()
    }
}