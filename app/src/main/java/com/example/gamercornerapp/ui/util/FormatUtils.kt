package com.example.gamercornerapp.ui.util

import java.util.Locale

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
