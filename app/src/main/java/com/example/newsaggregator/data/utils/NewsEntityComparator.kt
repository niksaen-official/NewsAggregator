package com.example.newsaggregator.data.utils

import com.example.newsaggregator.data.local.entity.NewsWithRelations
import com.example.newsaggregator.data.remote.dto.ItemDto

class NewsComparator {
    fun areTheSame(item1: ItemDto,item2: ItemDto): Boolean{
        val c1 = item1.title == item2.title
        val c2 = item1.link == item2.link
        val c3 = item1.description == item2.description
        val c4 = item1.pubDate == item2.pubDate
        val c5 = item1.guid == item2.guid
        return c1 && c2 && c3 && c4 && c5
    }

    fun notTheSame(item1: ItemDto,item2: ItemDto): Boolean{
        return !areTheSame(item1,item2)
    }
}