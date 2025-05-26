package com.example.newsaggregator.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.newsaggregator.data.remote.dto.ItemDto

data class NewsWithRelations(
    @Embedded
    val news: NewsEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "newsId"
    )
    val categories: List<CategoryEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "newsId"
    )
    val contents: List<ContentEntity>
)