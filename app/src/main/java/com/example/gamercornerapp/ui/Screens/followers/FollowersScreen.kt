package com.example.gamercornerapp.ui.Screens.followers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.followers.components.UserRow
import com.example.gamercornerapp.ui.componentes.GamerBottomBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersScreen(
    modifier: Modifier = Modifier
) {
    FollowersScreenContent(
        modifier = modifier
    )
}


@Composable
fun FollowersScreenContent(
    modifier: Modifier = Modifier
) {

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


@Preview(showBackground = true)
@Composable
fun FollowersScreenPreview() {

    GamerCornerAppTheme {
        FollowersScreen()
    }
}