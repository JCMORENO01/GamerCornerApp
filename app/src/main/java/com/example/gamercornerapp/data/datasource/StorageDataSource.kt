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

    // TE QUIERO MUCHO
    suspend fun uploadImage(imageUri: Uri, path: String): String {
        val storageRef = storage.reference.child(path)
        val uploadTask = storageRef.putFile(imageUri).await()
        val downloadUrl = uploadTask.storage.downloadUrl.await()
        return downloadUrl.toString()
    }
}