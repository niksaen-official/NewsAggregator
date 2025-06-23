package com.niksaengames.newsaggregator.core.di

import com.niksaengames.newsaggregator.data.local.dao.PostDao
import com.niksaengames.newsaggregator.data.remote.RssFeed
import com.niksaengames.newsaggregator.data.repository.PostLoaderRepositoryImpl
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