package com.example.newsaggregator.data.remote

import com.example.newsaggregator.data.remote.dto.RssDto
import retrofit2.http.GET
import retrofit2.http.Path

const val International = "world/europe-news"
const val LifeAndStyle = "uk/lifeandstyle"
const val Commentisfree = "uk/commentisfree"
const val Sport = "uk/sport"
const val Culture = "uk/culture"

interface RssFeed {
    @GET("/{query}/rss")
    suspend fun getRss(
        @Path("query") query: String
    ): RssDto
}