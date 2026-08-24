package com.example.gamercornerapp.ui.Screens.followers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.data.FollowerItem
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersHeader
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersList
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersTabs
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersScreen(
    modifier: Modifier = Modifier
) {

    val followers = LocalDataProvider.followers

    var selectedTabIndex by remember {
        mutableStateOf(0)
    }


    FollowersScreenContent(
        followers = followers,

        selectedTabIndex = selectedTabIndex,

        onTabSelected = {
            selectedTabIndex = it
        },

        modifier = modifier
    )
}


@Composable
fun FollowersScreenContent(
    followers: List<FollowerItem>,

    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )


        FollowersHeader()


        FollowersTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        FollowersList(
            followers = followers
        )
    }
}


@Preview(
    showBackground = true,
    name = "Followers Dark"
)
@Composable
fun FollowersScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        FollowersScreen()
    }
}