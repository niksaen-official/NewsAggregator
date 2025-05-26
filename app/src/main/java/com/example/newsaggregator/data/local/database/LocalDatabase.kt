package com.example.newsaggregator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsaggregator.data.local.dao.NewsDao
import com.example.newsaggregator.data.local.entity.CategoryEntity
import com.example.newsaggregator.data.local.entity.ContentEntity
import com.example.newsaggregator.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class, CategoryEntity::class, ContentEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}