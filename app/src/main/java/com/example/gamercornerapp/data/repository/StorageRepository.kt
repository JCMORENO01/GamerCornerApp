package com.example.gamercornerapp.data.repository

import android.net.Uri
import com.example.gamercornerapp.data.datasource.StorageDataSource
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val storageDataSource: StorageDataSource,
    private val authRepository: AuthRepository
) {
    suspend fun uploadProfileImage(imageUri: Uri): Result<String> {
        return try {
            // Limite de 15 segundos para manejar conexiones a internet (Te quiero mucho Angarita)
            val url = withTimeout(15_000L) {
                val userId = authRepository.currentUser?.uid ?: "guest_user"
                val path = "profile_images/$userId.jpg"
                storageDataSource.uploadImage(imageUri, path)
            }
            Result.success(url)
        } catch (e: TimeoutCancellationException) {
            Result.failure(Exception("Tiempo de espera agotado. Verifica que Storage esté activado en Firebase Console o tu conexión."))
        } catch (e: StorageException) {
            Result.failure(Exception("Error de Firebase Storage: ${e.localizedMessage ?: "Verifica las reglas o la conexión"}"))
        } catch (e: Exception) {
            Result.failure(Exception("Error al subir imagen: ${e.localizedMessage ?: "Inténtalo de nuevo"}"))
        }
    }
}