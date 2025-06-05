package com.example.newsaggregator.data.repository

import android.util.Log
import com.example.newsaggregator.core.extensions.formatDate
import com.example.newsaggregator.core.extensions.removeHtmlTags
import com.example.newsaggregator.data.convertors.NewsConverter
import com.example.newsaggregator.data.local.dao.PostDao
import com.example.newsaggregator.data.remote.RssFeed
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.domain.repository.LoaderRepository

class PostLoaderRepositoryImpl(
    private val rssFeed: RssFeed,
    private val postDao: PostDao) : LoaderRepository<ItemDto>{

    private val converter = NewsConverter()

    override suspend fun loadRemote(category:String): List<ItemDto> {
        try {
            postDao.clearAllNews(category)
            val channel = rssFeed.getRss(category).channel
            return channel.items.map {
                val i = it.copy(
                    description = it.description.removeHtmlTags(),
                    pubDate = it.pubDate.formatDate()
                )
                saveToLocal(i,category)
                return@map i
            }
        } catch (e: Exception) {
            Log.d("PostLoaderRepositoryImpl",e.message.toString())
            return emptyList()
        }
    }

    override suspend fun saveToLocal(item: ItemDto,category: String) {
        postDao.insertFullNews(converter.toEntity(item,category))
    }

    override suspend fun loadLocalSavedNews(category: String): List<ItemDto> {
        return postDao.getAllNewsWithRelations(category).map {
            converter.toDto(it)
        }
    }
}