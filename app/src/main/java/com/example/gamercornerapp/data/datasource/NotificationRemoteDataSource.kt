
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.NotificationItem
import kotlinx.coroutines.flow.Flow

interface NotificationRemoteDataSource {

    fun listenNotifications(
        userId: String
    ): Flow<List<NotificationItem>>
}
