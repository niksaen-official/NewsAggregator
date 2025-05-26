package com.example.newsaggregator.core.di

import android.content.Context
import androidx.room.Room
import com.example.newsaggregator.data.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun localDatabase(@ApplicationContext context:Context) =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java, "news-database"
        ).build()

    @Provides
    fun newsDao(localDatabase: LocalDatabase) = localDatabase.newsDao()
}