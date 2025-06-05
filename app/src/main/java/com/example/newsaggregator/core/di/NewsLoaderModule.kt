package com.example.newsaggregator.core.di

import com.example.newsaggregator.data.local.dao.PostDao
import com.example.newsaggregator.data.remote.RssFeed
import com.example.newsaggregator.data.repository.PostLoaderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PostLoadersModule {
    @Provides
    @Singleton
    fun newsLoader(rssFeed: RssFeed,newsDao: PostDao) = PostLoaderRepositoryImpl(rssFeed,newsDao)
}