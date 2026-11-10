
package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.MapReviewRemoteDataSource
import com.example.gamercornerapp.data.dto.ReviewDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MapReviewFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : MapReviewRemoteDataSource {

    override fun listenRecentReviews(
        sinceMillis: Long
    ): Flow<List<ReviewDTO>> = callbackFlow {

        val listener = firestore
            .collection("reviews")
            .whereGreaterThanOrEqualTo(
                "createdAtMillis",
                sinceMillis
            )
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val reviews = snapshot
                    ?.documents
                    ?.mapNotNull { document ->
                        document.toObject(
                            ReviewDTO::class.java
                        )?.copy(id = document.id)
                    }
                    ?: emptyList()

                trySend(reviews)
            }

        awaitClose {
            listener.remove()
        }
    }
}
