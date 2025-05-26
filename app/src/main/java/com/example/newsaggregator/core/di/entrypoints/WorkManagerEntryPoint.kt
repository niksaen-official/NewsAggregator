package com.example.newsaggregator.core.di.entrypoints

import com.example.newsaggregator.data.workers.WorkManagerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface WorkManagerEntryPoint {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WorkManagerEntryPoint {
        fun workManagerInitializer(): WorkManagerInitializer
    }
}