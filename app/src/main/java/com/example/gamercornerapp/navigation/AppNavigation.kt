package com.example.gamercornerapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.ui.Screens.explore.ExploreScreen
import com.example.gamercornerapp.ui.Screens.feed.FeedScreen
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.Screens.login.LoginScreen
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsScreen
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.review.ReviewScreen
import com.example.gamercornerapp.ui.Screens.selfProfile.SelfProfileScreen
import com.example.gamercornerapp.ui.Screens.startpage.StartApp
import com.example.gamercornerapp.ui.Screens.videogame.VideogameScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route,
        modifier = modifier
    ) {


        // START
        composable(
            route = Screen.Start.route
        ) {

            StartApp(
                onLoginClick = {

                    navController.navigate(
                        Screen.Login.route
                    )
                },
                onRegisterClick = {

                    navController.navigate(
                        Screen.Register.route
                    )
                }
            )
        }


        // LOGIN
        composable(
            route = Screen.Login.route
        ) {

            LoginScreen(
                onLoginClick = {

                    navController.navigate(
                        Screen.Feed.route
                    ) {

                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },

                onCreateAccountClick = {

                    navController.navigate(
                        Screen.Register.route
                    )
                }
            )
        }


        // REGISTER
        composable(
            route = Screen.Register.route
        ) {

            RegisterScreen(

                onRegisterClick = {

                    navController.navigate(
                        Screen.Feed.route
                    ) {

                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },

                onLoginClick = {

                    navController.navigate(
                        Screen.Login.route
                    )
                }
            )
        }


        // FEED
        composable(
            route = Screen.Feed.route
        ) {

            FeedScreen(
                onGameClick = { gameId ->

                    navController.navigate(
                        Screen.Videogame.createRoute(
                            gameId
                        )
                    )
                }
            )
        }


        // EXPLORE
        composable(
            route = Screen.Explore.route
        ) {

            ExploreScreen(

                onPopularGameClick = { game ->

                    navController.navigate(
                        Screen.Videogame.createRoute(
                            game.id
                        )
                    )
                },

                onResultGameClick = { game ->

                    navController.navigate(
                        Screen.Videogame.createRoute(
                            game.id
                        )
                    )
                }
            )
        }


        // NOTIFICATIONS
        composable(
            route = Screen.Notifications.route
        ) {

            NotificationsScreen()
        }


        // FOLLOWERS
        composable(
            route = Screen.Followers.route
        ) {

            FollowersScreen()
        }


        // SELF PROFILE
        composable(
            route = Screen.SelfProfile.route
        ) {

            SelfProfileScreen(
                onFollowersClick = {

                    navController.navigate(
                        Screen.Followers.route
                    )
                }
            )
        }


        // VIDEOGAME
        composable(
            route = Screen.Videogame.route,

            arguments = listOf(

                navArgument(
                    name = "gameId"
                ) {
                    type = NavType.IntType
                }
            )

        ) { backStackEntry ->


            val gameId =
                backStackEntry.arguments
                    ?.getInt("gameId")
                    ?: 0


            val allGames =
                LocalDataProvider.exploreResults +
                        LocalDataProvider.popularGames +
                        LocalDataProvider.posts.map {
                            it.game
                        }


            val game =
                allGames.find {
                    it.id == gameId
                }


            if (game != null) {

                VideogameScreen(
                    game = game,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onWriteReviewClick = {

                        navController.navigate(
                            Screen.Review.createRoute(
                                game.id
                            )
                        )
                    }
                )

            } else {

                Text(
                    text = "Juego no encontrado"
                )
            }
        }


        // REVIEW
        composable(
            route = Screen.Review.route,

            arguments = listOf(

                navArgument(
                    name = "gameId"
                ) {
                    type = NavType.IntType
                }
            )

        ) { backStackEntry ->


            val gameId =
                backStackEntry.arguments
                    ?.getInt("gameId")
                    ?: 0


            val allGames =
                LocalDataProvider.exploreResults +
                        LocalDataProvider.popularGames +
                        LocalDataProvider.posts.map {
                            it.game
                        }


            val game =
                allGames.find {
                    it.id == gameId
                }


            if (game != null) {

                ReviewScreen(
                    game = game,

                    onPublishClick = {

                        navController.popBackStack()
                    }
                )

            } else {

                Text(
                    text = "Juego no encontrado"
                )
            }
        }
    }
}