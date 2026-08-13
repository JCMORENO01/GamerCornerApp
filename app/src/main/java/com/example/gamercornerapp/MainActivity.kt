package com.example.gamercornerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.Screens.login.LoginScreen
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.review.ReviewScreen
import com.example.gamercornerapp.ui.Screens.startpage.StartApp
import com.example.gamercornerapp.ui.model.Game

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamerCornerAppTheme {
                val game = Game(
                    title = "Cyberpunk 2026",
                    developer = "CD Projekt Red",
                    year = 2020,
                    image = R.drawable.cyberpunk
                )
                ReviewScreen(game = game)
            }
        }
    }
}