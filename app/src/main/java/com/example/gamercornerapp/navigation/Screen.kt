
package com.example.gamercornerapp.navigation

import android.net.Uri

sealed class Screen(
    val route: String
) {
    data object Splash : Screen("splash")

    data object Start : Screen("start")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Feed : Screen("feed")

    data object Explore : Screen("explore")

    data object Notifications : Screen("notifications")

    data object Followers : Screen("followers/{userId}") {
        fun createRoute(userId: String): String {
            return "followers/${Uri.encode(userId)}"
        }
    }

    data object SelfProfile : Screen("self_profile")

    data object EditProfile : Screen("edit_profile")

    data object SearchUsers : Screen("search_users")

    data object UserProfile : Screen("user_profile/{userId}") {
        fun createRoute(userId: String): String {
            return "user_profile/${Uri.encode(userId)}"
        }
    }

    data object Videogame : Screen("videogame/{gameId}") {
        fun createRoute(gameId: String): String {
            return "videogame/${Uri.encode(gameId)}"
        }
    }

    data object Review : Screen(
        "review/{gameId}?reviewId={reviewId}&opinion={opinion}&rating={rating}"
    ) {
        fun createRoute(
            gameId: String,
            reviewId: String? = null,
            opinion: String? = null,
            rating: Int? = null
        ): String {
            val encodedGameId = Uri.encode(gameId)
            var routeStr = "review/$encodedGameId"

            val params = mutableListOf<String>()

            if (!reviewId.isNullOrEmpty()) {
                params.add("reviewId=${Uri.encode(reviewId)}")
            }

            if (!opinion.isNullOrEmpty()) {
                params.add("opinion=${Uri.encode(opinion)}")
            }

            if (rating != null) {
                params.add("rating=$rating")
            }

            if (params.isNotEmpty()) {
                routeStr += "?" + params.joinToString("&")
            }

            return routeStr
        }
    }

    data object RecoverPassword : Screen("recover_password")
}
