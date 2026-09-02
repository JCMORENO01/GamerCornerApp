package com.example.gamercornerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamercornerapp.ui.Screens.explore.ExploreScreen
import com.example.gamercornerapp.ui.Screens.explore.ExploreViewModel
import com.example.gamercornerapp.ui.Screens.feed.FeedScreen
import com.example.gamercornerapp.ui.Screens.feed.FeedViewModel
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.Screens.followers.FollowersViewModel
import com.example.gamercornerapp.ui.Screens.login.LoginScreen
import com.example.gamercornerapp.ui.Screens.login.LoginViewModel
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsScreen
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsViewModel
import com.example.gamercornerapp.ui.Screens.recoverPassword.RecoverPasswordScreen
import com.example.gamercornerapp.ui.Screens.recoverPassword.RecoverPasswordViewModel
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.register.RegisterViewModel
import com.example.gamercornerapp.ui.Screens.review.ReviewScreen
import com.example.gamercornerapp.ui.Screens.review.ReviewViewModel
import com.example.gamercornerapp.ui.Screens.selfProfile.SelfProfileScreen
import com.example.gamercornerapp.ui.Screens.selfProfile.SelfProfileViewModel
import com.example.gamercornerapp.ui.Screens.startpage.StartApp
import com.example.gamercornerapp.ui.Screens.videogame.VideogameScreen
import com.example.gamercornerapp.ui.Screens.videogame.VideogameViewModel


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
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(
                viewModel = viewModel,
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
                },

                onForgotPasswordClick = {

                    navController.navigate(
                        Screen.RecoverPassword.route
                    )
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        // RECOVER PASSWORD
        composable(
            route = Screen.RecoverPassword.route
        ) {
            val viewModel: RecoverPasswordViewModel = viewModel()
            RecoverPasswordScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSendLinkClick = { email ->
                    // Aquí iría la lógica para enviar el correo
                    navController.popBackStack()
                }
            )
        }


        // REGISTER
        composable(
            route = Screen.Register.route
        ) {
            val viewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                viewModel = viewModel,
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
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        // FEED
        composable(
            route = Screen.Feed.route
        ) {
            val viewModel: FeedViewModel = viewModel()
            FeedScreen(
                viewModel = viewModel,
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
            val viewModel: ExploreViewModel = viewModel()
            ExploreScreen(
                viewModel = viewModel,
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
            val viewModel: NotificationsViewModel = viewModel()
            NotificationsScreen(
                viewModel = viewModel
            )
        }


        // FOLLOWERS
        composable(
            route = Screen.Followers.route
        ) {
            val viewModel: FollowersViewModel = viewModel()
            FollowersScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        // SELF PROFILE
        composable(
            route = Screen.SelfProfile.route
        ) {
            val viewModel: SelfProfileViewModel = viewModel()
            SelfProfileScreen(
                viewModel = viewModel,
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

            val viewModel: VideogameViewModel = viewModel()

            VideogameScreen(
                gameId = gameId,
                viewModel = viewModel,

                onBackClick = {

                    navController.popBackStack()
                },

                onWriteReviewClick = {

                    navController.navigate(
                        Screen.Review.createRoute(
                            gameId
                        )
                    )
                }
            )
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

            val viewModel: ReviewViewModel = viewModel()

            ReviewScreen(
                gameId = gameId,
                viewModel = viewModel,

                onPublishClick = {

                    navController.popBackStack()
                }
            )
        }
    }
}
