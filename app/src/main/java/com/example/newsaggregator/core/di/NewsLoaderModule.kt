package com.example.newsaggregator.core.di

import com.example.newsaggregator.data.local.dao.NewsDao
import com.example.newsaggregator.data.remote.RssFeed
import com.example.newsaggregator.data.repository.NewsLoaderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsLoaderModule {
    @Provides
    @Singleton
    fun newsLoader(rssFeed: RssFeed,newsDao: NewsDao) =
        NewsLoaderRepositoryImpl(rssFeed,newsDao)
}