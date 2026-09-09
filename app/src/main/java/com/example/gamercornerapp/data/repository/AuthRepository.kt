package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) {
    suspend fun signIn(email: String, pass: String) {
        remoteDataSource.signIn(email, pass)
    }

    suspend fun signUp(email: String, pass: String) {
        remoteDataSource.signUp(email, pass)
    }

    fun signOut() {
        remoteDataSource.signOut()
    }

    val currentUser: FirebaseUser?
        get() = remoteDataSource.currentUser
}
