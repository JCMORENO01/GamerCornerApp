package com.example.gamercornerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamerCornerAppTheme {
                LoginScreen()
            }
        }
    }
}