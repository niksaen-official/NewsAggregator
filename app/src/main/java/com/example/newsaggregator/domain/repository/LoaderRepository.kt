package com.example.newsaggregator.domain.repository

interface LoaderRepository<T> {
    suspend fun loadRemote(): List<T>
    suspend fun saveToLocal(item:T)
    suspend fun loadLocalSavedNews(): List<T>
}