package com.example.newsaggregator.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsaggregator.data.repository.NewsLoaderRepositoryImpl
import com.example.newsaggregator.data.utils.NewsComparator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackgroundLoaderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val loader: NewsLoaderRepositoryImpl
) : CoroutineWorker(context, workerParams) {

    val comparator = NewsComparator()

    override suspend fun doWork(): Result {
        val list = loader.loadRemote()
        val localList = loader.loadLocalSavedNews()
        localList.forEach {
            val lItem = it
            list.forEach {
                if(comparator.notTheSame(lItem,it)){
                    loader.saveToLocal(it)
                }
            }
        }
        return Result.success()
    }
}