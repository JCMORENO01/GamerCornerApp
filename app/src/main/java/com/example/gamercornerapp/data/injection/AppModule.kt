package com.example.gamercornerapp.data.injection

import android.content.Context
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSource
import com.example.gamercornerapp.data.datasource.GamerCornerRemoteDataSourceImpl
import com.example.gamercornerapp.data.datasource.services.GamerCornerService
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
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
    fun provideFirebaseAuth(@ApplicationContext context: Context): FirebaseAuth {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.203:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGamerCornerService(retrofit: Retrofit): GamerCornerService {
        return retrofit.create(GamerCornerService::class.java)
    }

    @Provides
    @Singleton
    fun provideGamerCornerRemoteDataSource(
        impl: GamerCornerRemoteDataSourceImpl
    ): GamerCornerRemoteDataSource {
        return impl
    }
}
