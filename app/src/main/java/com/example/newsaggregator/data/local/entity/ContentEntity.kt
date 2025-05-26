package com.example.newsaggregator.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContentEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val type: String?,
    val width: String?,
    val url: String,
    @Embedded val credit: CreditEntity?,
    val newsId: Int
)