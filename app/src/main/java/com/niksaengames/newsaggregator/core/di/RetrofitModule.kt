package com.niksaengames.newsaggregator.core.di

import com.niksaengames.newsaggregator.data.remote.RssFeed
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://www.theguardian.com/")
            .addConverterFactory(XML.asConverterFactory("application/xml; charset=UTF8".toMediaType()))
            .build()

    @Provides
    fun rss(retrofit: Retrofit): RssFeed = retrofit.create(RssFeed::class.java)
}