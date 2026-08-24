package com.example.gamercornerapp.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

// Representa una categoria/genero de videojuego usada como chip filtrable
// en la pantalla de Explorar
data class GameCategory(
    val name: String,
    val icon: ImageVector
)
