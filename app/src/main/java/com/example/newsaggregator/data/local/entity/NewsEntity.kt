package com.example.newsaggregator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title: String,
    val link: String,
    var description: String,
    var pubDate: String,
    val guid: String,
    val dcCreator: String,
    val dcDate: String
)
