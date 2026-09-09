package com.example.gamercornerapp.ui.Screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToStart: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val navigateToHome by viewModel.navigateToHome.collectAsState()
    val navigateToStart by viewModel.navigateToStart.collectAsState()

    LaunchedEffect(navigateToHome, navigateToStart) {
        if (navigateToHome) {
            onNavigateToHome()
        } else if (navigateToStart) {
            onNavigateToStart()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}
