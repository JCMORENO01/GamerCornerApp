
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGamerCornerService(
        retrofit: Retrofit
    ): GamerCornerService {
        return retrofit.create(GamerCornerService::class.java)
    }

    @Provides
    @Singleton
    fun provideGameRemoteDataSource(
        impl: GameFirestoreDataSourceImpl
    ): GameRemoteDataSource {
        return impl
    }

    @Provides
    @Singleton
    fun provideReviewRemoteDataSource(
        impl: ReviewFirestoreDataSourceImpl
    ): ReviewRemoteDataSource {
        return impl
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        impl: UserFirestoreDataSourceImpl
    ): UserRemoteDataSource {
        return impl
    }

    @Provides
    @Singleton
    fun provideNotificationRemoteDataSource(
        impl: NotificationFirestoreDataSourceImpl
    ): NotificationRemoteDataSource {
        return impl
    }
}
