package com.niksaengames.newsaggregator.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.niksaengames.newsaggregator.data.repository.PostLoaderRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackgroundNewsLoaderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private var postLoaderRepositoryImpl: PostLoaderRepositoryImpl, ) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try{
            postLoaderRepositoryImpl.loadAll()
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }
    }
}