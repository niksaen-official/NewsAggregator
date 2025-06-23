package com.niksaengames.newsaggregator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.niksaengames.newsaggregator.data.remote.dto.CategoryDto

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val domain: String,
    val value: String,
    val newsId: Int
)