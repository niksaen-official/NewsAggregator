package com.niksaengames.newsaggregator.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.niksaengames.newsaggregator.data.local.dao.PostDao
import com.niksaengames.newsaggregator.data.local.entity.CategoryEntity
import com.niksaengames.newsaggregator.data.local.entity.ContentEntity
import com.niksaengames.newsaggregator.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class, CategoryEntity::class, ContentEntity::class],
    version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): PostDao
}