
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
    // CONFIGURACIÓN DE FUENTE DE DATOS
    // ==========================================

    private const val USE_RETROFIT = false

    // false = Firebase
    // true  = Retrofit + Express + PostgreSQL

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

        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
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
    // GAMES
    // ==========================================

    @Provides
    @Singleton
    fun provideGameRemoteDataSource(
        firestore: GameFirestoreDataSourceImpl,
        retrofit: GameRetrofitDataSourceImpl
    ): GameRemoteDataSource {

        return if (USE_RETROFIT) {
            retrofit
        } else {
            firestore
        }
    }

    // ==========================================
    // REVIEWS
    // ==========================================

    @Provides
    @Singleton
    fun provideReviewRemoteDataSource(
        firestore: ReviewFirestoreDataSourceImpl,
        retrofit: ReviewRetrofitDataSourceImpl
    ): ReviewRemoteDataSource {

        return if (USE_RETROFIT) {
            retrofit
        } else {
            firestore
        }
    }

    // ==========================================
    // USERS
    // ==========================================

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        firestore: UserFirestoreDataSourceImpl,
        retrofit: UserRetrofitDataSourceImpl
    ): UserRemoteDataSource {

        return if (USE_RETROFIT) {
            retrofit
        } else {
            firestore
        }
    }

    // ==========================================
    // NOTIFICATIONS
    // ==========================================

    @Provides
    @Singleton
    fun provideNotificationRemoteDataSource(
        impl: NotificationFirestoreDataSourceImpl
    ): NotificationRemoteDataSource {
        return impl
    }
}
