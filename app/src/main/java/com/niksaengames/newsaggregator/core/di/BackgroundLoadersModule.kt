package com.niksaengames.newsaggregator.core.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import com.niksaengames.newsaggregator.data.workers.BackgroundNewsLoaderScheduler
import com.niksaengames.newsaggregator.data.workers.BackgroundNewsLoaderWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BackgroundLoadersModule {
    @Provides
    fun backgroundNewsLoaderScheduler(
        @ApplicationContext context: Context,
        workerFactory: HiltWorkerFactory) = BackgroundNewsLoaderScheduler(context,workerFactory)
}