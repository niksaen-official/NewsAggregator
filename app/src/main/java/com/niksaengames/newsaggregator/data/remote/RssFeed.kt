package com.niksaengames.newsaggregator.data.remote

import com.niksaengames.newsaggregator.data.remote.dto.RssDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RssFeed {
    @GET("{query}/rss")
    suspend fun getRss(
        @Path("query") query: String = "world"
    ): RssDto
}