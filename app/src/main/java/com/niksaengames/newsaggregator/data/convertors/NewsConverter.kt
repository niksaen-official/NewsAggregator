package com.niksaengames.newsaggregator.data.convertors

import com.niksaengames.newsaggregator.data.local.entity.CategoryEntity
import com.niksaengames.newsaggregator.data.local.entity.ContentEntity
import com.niksaengames.newsaggregator.data.local.entity.CreditEntity
import com.niksaengames.newsaggregator.data.local.entity.PostEntity
import com.niksaengames.newsaggregator.data.local.entity.PostWithRelations
import com.niksaengames.newsaggregator.data.remote.dto.CategoryDto
import com.niksaengames.newsaggregator.data.remote.dto.ContentDto
import com.niksaengames.newsaggregator.data.remote.dto.CreditDto
import com.niksaengames.newsaggregator.data.remote.dto.ItemDto

class NewsConverter {
    fun toDto(newsWithRelations: PostWithRelations): ItemDto {
        return ItemDto(
            newsWithRelations.news.title,
            newsWithRelations.news.link,
            newsWithRelations.news.description,
            toCategoryDtoList(newsWithRelations.categories),
            newsWithRelations.news.pubDate,
            newsWithRelations.news.guid,
            toContentDtoList(newsWithRelations.contents),
            newsWithRelations.news.dcCreator,
            newsWithRelations.news.dcDate
        )
    }
    private fun toCategoryDtoList(list: List<CategoryEntity>): List<CategoryDto> {
        return list.map { CategoryDto(it.domain, it.value) }
    }

    private fun toContentDtoList(list: List<ContentEntity>): List<ContentDto> {
        return list.map {
            ContentDto(
                it.type,
                it.width,
                it.url,
                it.credit?.let { c -> CreditDto(c.scheme, c.value) }
            )
        }
    }

    fun toEntity(news: ItemDto,category:Int,subcategory:Int): PostWithRelations {
        val newsEntity = PostEntity(
            0,
            news.title,
            news.link,
            news.description,
            news.pubDate,
            news.guid,
            news.dcCreator,
            news.dcDate,
            category,
            subcategory
        )
        return PostWithRelations(
            newsEntity,
            toCategoriesEntityList(news.categories),
            toContentEntityList(news.contents)
        )
    }
    private fun toCategoriesEntityList(list: List<CategoryDto>): List<CategoryEntity>{
        return list.map {
            CategoryEntity(0, it.domain, it.value, 0)
        }
    }
    private fun toContentEntityList(list: List<ContentDto>): List<ContentEntity>{
        return list.map {
            ContentEntity(
                0,
                it.type,
                it.width,
                it.url,
                CreditEntity(it.credit?.scheme, it.credit?.value ?: ""),
                0)
        }
    }
}