package com.example.gamercornerapp.ui.util

import java.util.Locale

// Formatea un numero de reseñas grande de forma compacta.
// Ej: 2100 -> "2.1k", 850 -> "850"
fun formatReviewsCount(count: Int): String {

    return if (count >= 1000) {

        String.format(
            Locale.getDefault(),
            "%.1fk",
            count / 1000f
        )

    } else {

        count.toString()
    }
}
