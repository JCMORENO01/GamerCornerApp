package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.datasource.AuthRemoteDataSource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) {
    suspend fun signIn(email: String, pass: String): Result<Unit> {
        return try {
            remoteDataSource.signIn(email, pass)
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(e)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(e)
        } catch (e: FirebaseNetworkException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, pass: String): Result<Unit> {
        return try {
            remoteDataSource.signUp(email, pass)
            Result.success(Unit)
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(e)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(e)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(e)
        } catch (e: FirebaseNetworkException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        remoteDataSource.signOut()
    }

    suspend fun updateProfilePicture(photoUrl: String): Result<Unit> {
        return try {
            remoteDataSource.updateProfilePicture(photoUrl)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    val currentUser: FirebaseUser?
        get() = remoteDataSource.currentUser
}
