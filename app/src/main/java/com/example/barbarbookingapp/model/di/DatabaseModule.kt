package com.example.barbarbookingapp.model.di

import android.content.Context
import com.example.barbarbookingapp.model.AppDatabase
import com.example.barbarbookingapp.model.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDatabase(context) ?: throw IllegalStateException("AppDatabase is not initialized.")
    }
    @Provides
    @Singleton
    fun getUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }
}