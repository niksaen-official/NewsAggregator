package com.example.newsaggregator.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.newsaggregator.domain.repository.BackgroundDataLoaderScheduler
import java.util.concurrent.TimeUnit

class BackgroundNewsLoaderScheduler (
    private val context: Context,
    private val workerFactory: HiltWorkerFactory
) : BackgroundDataLoaderScheduler{
    override fun schedule() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequest.Builder(
            BackgroundNewsLoaderWorker::class.java,
            20, TimeUnit.MINUTES,
            15, TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NewsSyncWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }
}