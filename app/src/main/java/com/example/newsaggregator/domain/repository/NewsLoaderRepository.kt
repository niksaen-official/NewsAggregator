package com.example.newsaggregator.domain.repository

import com.example.newsaggregator.data.local.dao.NewsDao
import com.example.newsaggregator.data.remote.RssFeed

interface NewsLoaderRepository<T> {
    suspend fun loadRemote(): List<T>
    suspend fun saveToLocal(item:T)
    suspend fun loadLocalSavedNews(): List<T>
}