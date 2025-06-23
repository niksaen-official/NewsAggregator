package com.niksaengames.newsaggregator.domain.repository

interface NetworkStatusProvider {
    fun isOnline(): Boolean
}