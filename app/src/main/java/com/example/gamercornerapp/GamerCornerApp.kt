package com.example.gamercornerapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gamercornerapp.navigation.AppNavigation
import com.example.gamercornerapp.navigation.NavigationLogic
import com.example.gamercornerapp.ui.componentes.GamerBottomBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun GamerCornerApp() {

    GamerCornerAppTheme {

        val navController =
            rememberNavController()


        val navBackStackEntry by
        navController.currentBackStackEntryAsState()


        val currentRoute =
            navBackStackEntry
                ?.destination
                ?.route


        Scaffold(
            containerColor =
                MaterialTheme.colorScheme.background,

            bottomBar = {

                if (
                    NavigationLogic.shouldShowBottomBar(
                        currentRoute
                    )
                ) {

                    GamerBottomBar(
                        navController = navController
                    )
                }
            }

        ) { innerPadding ->

            AppNavigation(
                navController = navController,

                modifier = Modifier.padding(
                    innerPadding
                )
            )
        }
    }
}