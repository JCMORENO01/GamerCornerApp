package com.example.gamercornerapp.data.datasource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageDataSource @Inject constructor(
    private val storage: FirebaseStorage
) {
    suspend fun uploadImage(imageUri: Uri, path: String): String {
        // Referencia de la carpeta/path en Firebase Storage
        val storageRef = storage.reference.child(path)

        // Subir archivo y esperar a que complete la tarea
        val uploadTask = storageRef.putFile(imageUri).await()

        // Obtener la URL de descarga desde la referencia de la tarea completada
        val downloadUrl = uploadTask.storage.downloadUrl.await()
        return downloadUrl.toString()
    }
}