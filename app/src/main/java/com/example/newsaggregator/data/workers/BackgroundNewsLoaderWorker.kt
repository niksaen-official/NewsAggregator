package com.example.newsaggregator.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsaggregator.data.remote.Commentisfree
import com.example.newsaggregator.data.remote.Culture
import com.example.newsaggregator.data.remote.International
import com.example.newsaggregator.data.remote.LifeAndStyle
import com.example.newsaggregator.data.remote.Sport
import com.example.newsaggregator.data.repository.PostLoaderRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackgroundNewsLoaderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private var postLoaderRepositoryImpl: PostLoaderRepositoryImpl, ) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try{
            postLoaderRepositoryImpl.loadRemote(International)
            postLoaderRepositoryImpl.loadRemote(Commentisfree)
            postLoaderRepositoryImpl.loadRemote(Sport)
            postLoaderRepositoryImpl.loadRemote(Culture)
            postLoaderRepositoryImpl.loadRemote(LifeAndStyle)
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }
    }
}