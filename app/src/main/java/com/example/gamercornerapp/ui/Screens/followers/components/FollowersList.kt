package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersList(
    modifier: Modifier = Modifier
) {

    val drakool = FollowerUser(
        name = R.string.mock_user_drakool,
        handle = R.string.mock_handle_drakool,
        image = R.drawable.messi1,
        isFollowing = false
    )

    val ladyAki = FollowerUser(
        name = R.string.mock_user_ladyaki,
        handle = R.string.mock_handle_ladyaki,
        image = R.drawable.messi1,
        isFollowing = true
    )

    val shadowX = FollowerUser(
        name = R.string.mock_user_shadowx,
        handle = R.string.mock_handle_shadowx,
        image = R.drawable.messi2,
        isFollowing = false
    )

    val neoGamer = FollowerUser(
        name = R.string.mock_user_neogamer,
        handle = R.string.mock_handle_neogamer,
        image = R.drawable.messi3,
        isFollowing = false
    )

    val pixelPro = FollowerUser(
        name = R.string.mock_user_pixelpro,
        handle = R.string.mock_handle_pixelpro,
        image = R.drawable.messi4,
        isFollowing = false
    )

    val rogueMaster = FollowerUser(
        name = R.string.mock_user_roguemaster,
        handle = R.string.mock_handle_roguemaster,
        image = R.drawable.messi1,
        isFollowing = false
    )


    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        UserRow(
            user = drakool
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        UserRow(
            user = ladyAki
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        UserRow(
            user = shadowX
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        UserRow(
            user = neoGamer
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        UserRow(
            user = pixelPro
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        UserRow(
            user = rogueMaster
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FollowersListPreview() {

    GamerCornerAppTheme {

        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            FollowersList()
        }
    }
}