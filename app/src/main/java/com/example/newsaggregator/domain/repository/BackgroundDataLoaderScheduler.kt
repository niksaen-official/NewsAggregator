package com.example.newsaggregator.domain.repository

import androidx.work.CoroutineWorker

interface BackgroundDataLoaderScheduler{
    fun schedule()
}