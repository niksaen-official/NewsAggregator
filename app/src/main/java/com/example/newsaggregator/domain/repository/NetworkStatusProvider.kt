package com.example.newsaggregator.domain.repository

interface NetworkStatusProvider {
    fun isOnline(): Boolean
}