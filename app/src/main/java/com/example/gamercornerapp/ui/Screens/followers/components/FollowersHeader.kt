package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = 16.dp)
    ) {

        Text(
            text = stringResource(
                id = R.string.back_arrow
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .padding(
                    end = 12.dp
                )
        )


        Text(
            text = stringResource(
                id = R.string.title_followers
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FollowersHeaderPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            FollowersHeader(
                onBackClick = { }
            )
        }
    }
}


data class FollowerUser(
    val name: Int,
    val handle: Int,
    val image: Int,
    val isFollowing: Boolean
)