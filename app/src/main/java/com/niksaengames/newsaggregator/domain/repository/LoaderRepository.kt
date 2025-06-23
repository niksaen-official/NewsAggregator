package com.niksaengames.newsaggregator.domain.repository

interface LoaderRepository<T> {
    suspend fun loadRemote(category:Int,subcategory:Int = 0): List<T>
    suspend fun saveToLocal(item:T,category:Int,subcategory:Int = 0)
    suspend fun loadLocalSavedNews(category:Int,subcategory:Int = 0): List<T>
}