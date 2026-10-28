package com.example.gamercornerapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        if (BuildConfig.DEBUG) {
            try {
                FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080)
                FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099)
            } catch (_: Exception) {}
        }
    }
}
