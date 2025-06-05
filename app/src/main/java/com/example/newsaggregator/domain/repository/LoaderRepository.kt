package com.example.newsaggregator.domain.repository

interface LoaderRepository<T> {
    suspend fun loadRemote(category:String): List<T>
    suspend fun saveToLocal(item:T,category:String)
    suspend fun loadLocalSavedNews(category:String): List<T>
}