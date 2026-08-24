package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FollowerItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersList(
    followers: List<FollowerItem>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        followers.forEachIndexed { index, follower ->

            UserRow(
                user = follower
            )

            if (index < followers.lastIndex) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun FollowersListPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        val mockFollowers = listOf(

            FollowerItem(
                id = "1",
                username = "Drakool",
                handle = "@drakool",
                avatarImage = R.drawable.messi1,
                isFollowing = true
            ),

            FollowerItem(
                id = "2",
                username = "LadyAki",
                handle = "@ladyaki",
                avatarImage = R.drawable.messi2,
                isFollowing = false
            ),

            FollowerItem(
                id = "3",
                username = "ShadowX",
                handle = "@shadowx",
                avatarImage = R.drawable.messi3,
                isFollowing = true
            )
        )


        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            FollowersList(
                followers = mockFollowers
            )
        }
    }
}