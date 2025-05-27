package com.example.newsaggregator.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsaggregator.data.repository.NewsLoaderRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackgroundNewsLoaderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private var newsLoaderRepositoryImpl: NewsLoaderRepositoryImpl
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try{
            newsLoaderRepositoryImpl.loadRemote()
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }
    }
}