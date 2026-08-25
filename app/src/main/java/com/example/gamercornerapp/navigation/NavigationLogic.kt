package com.example.gamercornerapp.navigation


object NavigationLogic {

    private val bottomBarRoutes = listOf(
        Screen.Feed.route,
        Screen.Explore.route,
        Screen.Notifications.route,
        Screen.SelfProfile.route
    )


    fun shouldShowBottomBar(
        currentRoute: String?
    ): Boolean {

        return currentRoute in bottomBarRoutes
    }
}