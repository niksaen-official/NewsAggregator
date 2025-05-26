package com.example.newsaggregator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.newsaggregator.data.local.entity.CategoryEntity
import com.example.newsaggregator.data.local.entity.ContentEntity
import com.example.newsaggregator.data.local.entity.NewsEntity
import com.example.newsaggregator.data.local.entity.NewsWithRelations

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity): Long

    @Delete
    suspend fun deleteNews(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM CategoryEntity WHERE newsId = :newsId")
    suspend fun deleteCategoriesForNews(newsId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contents: List<ContentEntity>)

    @Query("DELETE FROM ContentEntity WHERE newsId = :newsId")
    suspend fun deleteContentsForNews(newsId: Long)


    @Transaction
    @Query("SELECT * FROM NewsEntity")
    suspend fun getAllNewsWithRelations(): List<NewsWithRelations>

    @Transaction
    suspend fun insertFullNews(newsWithRelations: NewsWithRelations) {
        val newsId = insertNews(newsWithRelations.news)

        val categories = newsWithRelations.categories.map {
            it.copy(newsId = newsId.toInt())
        }
        insertCategories(categories)

        val contents = newsWithRelations.contents.map {
            it.copy(newsId = newsId.toInt())
        }
        insertContents(contents)
    }

    @Transaction
    suspend fun deleteFullNews(newsWithRelations: NewsWithRelations) {
        val newsId = newsWithRelations.news.id
        deleteCategoriesForNews(newsId)
        deleteContentsForNews(newsId)
        deleteNews(newsWithRelations.news)
    }
}