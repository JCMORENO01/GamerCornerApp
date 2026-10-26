
package com.example.gamercornerapp.data.injection

import android.content.Context

import com.example.gamercornerapp.data.datasource.GameRemoteDataSource
import com.example.gamercornerapp.data.datasource.ReviewRemoteDataSource
import com.example.gamercornerapp.data.datasource.UserRemoteDataSource
import com.example.gamercornerapp.data.datasource.NotificationRemoteDataSource

import com.example.gamercornerapp.data.datasource.impl.firestore.GameFirestoreDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.firestore.NotificationFirestoreDataSourceImpl

import com.example.gamercornerapp.data.datasource.impl.retrofit.GameRetrofitDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.retrofit.ReviewRetrofitDataSourceImpl
import com.example.gamercornerapp.data.datasource.impl.retrofit.UserRetrofitDataSourceImpl

import com.example.gamercornerapp.data.datasource.services.GamerCornerService

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.gamercornerapp.BuildConfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ==========================================
    // FIREBASE
    // ==========================================

    @Provides
    @Singleton
    fun provideFirebaseAuth(
        @ApplicationContext context: Context
    ): FirebaseAuth {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }
        val auth = FirebaseAuth.getInstance()
        try {
            if (BuildConfig.DEBUG) {
                auth.useEmulator("10.0.2.2", 9099)
            }
        } catch (_: Exception) {
            // Already connected or emulator not running
        }
        return auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()
        try {
            if (BuildConfig.DEBUG) {
                firestore.useEmulator("10.0.2.2", 8080)
            }
        } catch (_: Exception) {
            // Already connected or emulator not running
        }
        return firestore
    }

    // ==========================================
    // RETROFIT
    // ==========================================

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(
                ScalarsConverterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideGamerCornerService(
        retrofit: Retrofit
    ): GamerCornerService {
        return retrofit.create(
            GamerCornerService::class.java
        )
    }

    // ==========================================
    // GAMES - FIREBASE ACTIVO
    // ==========================================

    @Provides
    @Singleton
    fun provideGameRemoteDataSource(
        impl: GameFirestoreDataSourceImpl
    ): GameRemoteDataSource {
        return impl
    }

    /*
    // GAMES - RETROFIT
    @Provides
    @Singleton
    fun provideGameRemoteDataSource(
        impl: GameRetrofitDataSourceImpl
    ): GameRemoteDataSource {
        return impl
    }
    */

    // ==========================================
    // REVIEWS - FIREBASE ACTIVO
    // ==========================================

    @Provides
    @Singleton
    fun provideReviewRemoteDataSource(
        impl: ReviewFirestoreDataSourceImpl
    ): ReviewRemoteDataSource {
        return impl
    }

    /*
    // REVIEWS - RETROFIT
    @Provides
    @Singleton
    fun provideReviewRemoteDataSource(
        impl: ReviewRetrofitDataSourceImpl
    ): ReviewRemoteDataSource {
        return impl
    }
    */

    // ==========================================
    // USERS - FIREBASE ACTIVO
    // ==========================================

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        impl: UserFirestoreDataSourceImpl
    ): UserRemoteDataSource {
        return impl
    }

    /*
    // USERS - RETROFIT
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        impl: UserRetrofitDataSourceImpl
    ): UserRemoteDataSource {
        return impl
    }
    */

    // ==========================================
    // NOTIFICATIONS - SIEMPRE FIREBASE
    // ==========================================

    @Provides
    @Singleton
    fun provideNotificationRemoteDataSource(
        impl: NotificationFirestoreDataSourceImpl
    ): NotificationRemoteDataSource {
        return impl
    }
}
