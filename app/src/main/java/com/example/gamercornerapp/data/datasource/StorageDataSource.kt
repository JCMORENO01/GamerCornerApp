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
        //referencia de la carpeta/path en Firebase Storage
        val storageRef = storage.reference.child(path)

        storageRef.putFile(imageUri).await()

        val downloadUrl = storageRef.downloadUrl.await()
        return downloadUrl.toString()
    }
}