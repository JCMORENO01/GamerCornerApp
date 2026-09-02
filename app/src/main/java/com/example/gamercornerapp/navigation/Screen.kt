package com.example.gamercornerapp.navigation


sealed class Screen(
    val route: String
) {

    data object Start : Screen(
        route = "start"
    )


    data object Login : Screen(
        route = "login"
    )


    data object Register : Screen(
        route = "register"
    )


    data object Feed : Screen(
        route = "feed"
    )


    data object Explore : Screen(
        route = "explore"
    )


    data object Notifications : Screen(
        route = "notifications"
    )


    data object Followers : Screen(
        route = "followers"
    )


    data object SelfProfile : Screen(
        route = "self_profile"
    )


    data object Videogame : Screen(
        route = "videogame/{gameId}"
    ) {

        fun createRoute(
            gameId: Int
        ): String {

            return "videogame/$gameId"
        }
    }


    data object Review : Screen(
        route = "review/{gameId}"
    ) {

        fun createRoute(
            gameId: Int
        ): String {

            return "review/$gameId"
        }
    }


    data object RecoverPassword : Screen(
        route = "recover_password"
    )
}