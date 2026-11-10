
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.data.datasource.MapReviewRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapReviewRepository @Inject constructor(
    private val dataSource: MapReviewRemoteDataSource
) {

    fun getRecentReviews(
        sinceMillis: Long
    ): Flow<List<MapReview>> {

        return dataSource.listenRecentReviews(sinceMillis)
            .map { reviews ->

                val now = System.currentTimeMillis()
                val last24Hours = 24L * 60 * 60 * 1000

                reviews.mapNotNull { review ->

                    val latitude = review.latitude
                        ?: return@mapNotNull null

                    val longitude = review.longitude
                        ?: return@mapNotNull null

                    val createdAt = review.createdAtMillis
                        ?: return@mapNotNull null

                    val isRecent =
                        createdAt in (now - last24Hours)..now

                    val validLocation =
                        latitude.isFinite() &&
                                longitude.isFinite() &&
                                latitude in -90.0..90.0 &&
                                longitude in -180.0..180.0

                    if (!isRecent || !validLocation) {
                        return@mapNotNull null
                    }

                    MapReview(
                        id = review.id,
                        gameTitle = review.game?.title
                            ?: "Videojuego",
                        username = review.user?.username
                            ?: "Gamer",
                        description = review.description,
                        rating = review.rating,
                        latitude = latitude,
                        longitude = longitude,
                        createdAtMillis = createdAt
                    )
                }.sortedByDescending {
                    it.createdAtMillis
                }
            }
    }
}
