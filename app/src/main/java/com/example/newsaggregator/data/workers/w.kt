package com.example.newsaggregator.data.workers

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkManagerInitializer @Inject constructor(
    private val workManager: WorkManager
) {
    fun scheduleBackgroundWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequest.Builder(
            BackgroundLoaderWorker::class.java,
            1, TimeUnit.HOURS,
            55, TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "background_loader_work",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}