package com.example.newsaggregator.core

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.example.newsaggregator.core.di.entrypoints.WorkManagerEntryPoint.WorkManagerEntryPoint
import com.example.newsaggregator.data.workers.BackgroundLoaderWorker
import com.example.newsaggregator.data.workers.WorkManagerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() , SingletonImageLoader.Factory {

    @Inject lateinit var workManagerInitializer: WorkManagerInitializer

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            WorkManagerEntryPoint::class.java
        )
        entryPoint.workManagerInitializer().scheduleBackgroundWork()
    }
}