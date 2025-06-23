package com.example.newsaggregator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsaggregator.data.local.entity.CategoryEntity
import com.example.newsaggregator.data.local.entity.ContentEntity
import com.example.newsaggregator.data.local.entity.PostEntity
import com.example.newsaggregator.data.local.entity.PostWithRelations

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: PostEntity): Long

    @Delete
    suspend fun deleteNews(news: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM CategoryEntity WHERE newsId = :newsId")
    suspend fun deleteCategoriesForNews(newsId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contents: List<ContentEntity>)

    @Query("DELETE FROM ContentEntity WHERE newsId = :newsId")
    suspend fun deleteContentsForNews(newsId: Long)


    @Transaction
    @Query("SELECT * FROM PostEntity WHERE mainCategory=:category AND subCategory=:subcategory")
    suspend fun getAllNewsWithRelations(category:Int,subcategory:Int): List<PostWithRelations>

    @Transaction
    suspend fun insertFullNews(newsWithRelations: PostWithRelations) {
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
    suspend fun deleteFullNews(newsWithRelations: PostWithRelations) {
        val newsId = newsWithRelations.news.id
        deleteCategoriesForNews(newsId)
        deleteContentsForNews(newsId)
        deleteNews(newsWithRelations.news)
    }

    @Query("DELETE FROM PostEntity WHERE mainCategory = :category AND subCategory=:subcategory")
    suspend fun clearAllNews(category:Int,subcategory:Int)

    @Query("DELETE FROM CategoryEntity")
    suspend fun clearAllCategories()

    @Query("DELETE FROM ContentEntity")
    suspend fun clearAllContents()

    @Transaction
    suspend fun clearAllData(category:Int,subcategory:Int) {
        clearAllContents()
        clearAllCategories()
        clearAllNews(category,subcategory)
    }
}