package com.example.newsaggregator.data.repository

import com.example.newsaggregator.data.convertors.NewsConverter
import com.example.newsaggregator.data.local.dao.NewsDao
import com.example.newsaggregator.data.remote.RssFeed
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.domain.repository.LoaderRepository
import javax.inject.Inject

class NewsLoaderRepositoryImpl @Inject constructor(
    private val rssFeed: RssFeed,
    private val newsDao: NewsDao) : LoaderRepository<ItemDto>{

    private val converter = NewsConverter()

    override suspend fun loadRemote(): List<ItemDto> {
        try {
            val channel = rssFeed.getRss().channel
            return channel.items.map {
                item -> item.copy(
                    description = removeHtmlTags(item.description),
                    pubDate = formatDate(item.pubDate)
                )
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun saveToLocal(item: ItemDto) {
        newsDao.insertFullNews(converter.toEntity(item))
    }

    override suspend fun loadLocalSavedNews(): List<ItemDto> {
        return newsDao.getAllNewsWithRelations().map {
            converter.toDto(it)
        }
    }

    private fun removeHtmlTags(htmlText: String): String {
        return htmlText.replace(Regex("<[^>]*>"), "")
    }

    private fun formatDate(date: String): String {
        val parts = date.split(" ")
        return "${parts[1]} ${parts[2]} ${parts[3]}"
    }
}