package com.example.gamercornerapp.ui.Screens.followers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersHeader
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersList
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersTabs
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
        FollowersHeader()


        // Botones seguidores y siguiendo
        FollowersTabs()


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Lista de usuarios
        FollowersList()


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