package com.example.gamercornerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gamercornerapp.ui.Screens.feed.FeedScreen
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsScreen
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.selfProfile.SelfProfileScreen
import com.example.gamercornerapp.ui.componentes.GamerBottomBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            GamerCornerAppTheme {

                var selectedTab by remember {
                    mutableStateOf(0)
                }

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,

                    bottomBar = {

                        if (
                            selectedTab == 0 ||
                            selectedTab == 2 ||
                            selectedTab == 3
                        ) {

                            GamerBottomBar(
                                selectedTab = selectedTab,
                                onTabSelected = {
                                    selectedTab = it
                                }
                            )
                        }
                    }

                ) { innerPadding ->

                    when (selectedTab) {

                        0 -> FeedScreen(
                            modifier = Modifier.padding(innerPadding)
                        )

                        2 -> FollowersScreen(
                            modifier = Modifier.padding(innerPadding)
                        )

                        3 -> SelfProfileScreen(
                            modifier = Modifier.padding(innerPadding)
                        )

                        else -> RegisterScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}