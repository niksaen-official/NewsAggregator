package com.example.newsaggregator.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithRelations(
    @Embedded
    val news: PostEntity,

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