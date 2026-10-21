
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.data.datasource.NotificationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource
) {

    fun listenNotifications(
        userId: String
    ): Flow<List<NotificationItem>> {
        return remoteDataSource.listenNotifications(userId)
    }
}
