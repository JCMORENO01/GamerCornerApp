
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.ReviewDTO
import kotlinx.coroutines.flow.Flow

interface MapReviewRemoteDataSource {

    fun listenRecentReviews(
        sinceMillis: Long
    ): Flow<List<ReviewDTO>>
}
