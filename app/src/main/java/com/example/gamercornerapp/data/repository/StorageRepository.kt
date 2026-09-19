package com.example.gamercornerapp.data.repository

import android.net.Uri
import com.example.gamercornerapp.data.datasource.StorageDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val storageDataSource: StorageDataSource,
    private val authRepository: AuthRepository //id usuario actual
) {
    suspend fun uploadProfileImage(imageUri: Uri): Result<String> {
        return try {
            val userId = authRepository.currentUser?.uid ?: throw Exception("Usuario no autenticado")
            val path = "profile_images/$userId.jpg"

            val url = storageDataSource.uploadImage(imageUri, path)
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}