package com.example.gamercornerapp.navigation

sealed class Screen(
    val route: String
) {

    data object Splash : Screen(
        route = "splash"
    )

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

    data object UserProfile : Screen(
        route = "user_profile/{userId}"
    ) {
        fun createRoute(userId: String): String {
            return "user_profile/$userId"
        }
    }

    data object Videogame : Screen(
        route = "videogame/{gameId}"
    ) {
        fun createRoute(gameId: String): String {
            return "videogame/$gameId"
        }
    }

    data object Review : Screen(
        route = "review/{gameId}?reviewId={reviewId}&opinion={opinion}&rating={rating}"
    ) {
        fun createRoute(
            gameId: String,
            reviewId: String? = null,
            opinion: String? = null,
            rating: Int? = null
        ): String {
            var routeStr = "review/$gameId"
            val params = mutableListOf<String>()
            if (!reviewId.isNullOrEmpty()) params.add("reviewId=$reviewId")
            if (!opinion.isNullOrEmpty()) params.add("opinion=$opinion")
            if (rating != null) params.add("rating=$rating")
            if (params.isNotEmpty()) {
                routeStr += "?" + params.joinToString("&")
            }
            return routeStr
        }
    }

    data object RecoverPassword : Screen(
        route = "recover_password"
    )
}
