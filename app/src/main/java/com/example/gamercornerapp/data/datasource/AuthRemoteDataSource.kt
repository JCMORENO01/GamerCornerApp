package com.example.gamercornerapp.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun signIn(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).await()
    }

    suspend fun signUp(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).await()
    }

    fun signOut() {
        auth.signOut()
    }


    suspend fun updateProfilePicture(photoUrl: String) {
        val user = auth.currentUser
        val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
            .setPhotoUri(android.net.Uri.parse(photoUrl))
            .build()

        user?.updateProfile(profileUpdates)?.await()
    }

    val currentUser: FirebaseUser?
        get() = auth.currentUser
}
