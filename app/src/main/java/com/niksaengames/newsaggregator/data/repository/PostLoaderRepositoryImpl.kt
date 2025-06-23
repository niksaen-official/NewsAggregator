package com.niksaengames.newsaggregator.data.repository

import com.niksaengames.newsaggregator.core.extensions.formatDate
import com.niksaengames.newsaggregator.core.extensions.removeHtmlTags
import com.niksaengames.newsaggregator.data.convertors.NewsConverter
import com.niksaengames.newsaggregator.data.local.dao.PostDao
import com.niksaengames.newsaggregator.data.remote.RssFeed
import com.niksaengames.newsaggregator.data.remote.dto.ItemDto
import com.niksaengames.newsaggregator.domain.repository.LoaderRepository

class PostLoaderRepositoryImpl(
    private val rssFeed: RssFeed,
    private val postDao: PostDao) : LoaderRepository<ItemDto>{

    private val converter = NewsConverter()
    private val subCategoriesRoutes = arrayOf(
        arrayOf(
            "world",
            "world/europe-news",
            "us-news",
            "world/americas",
            "world/asia",
            "australia-news",
            "world/middleeast",
            "world/africa",
            "inequality",
            "global-development"
        ),
        arrayOf(
            "profile/editorial",
            "index/contributors",
            "tone/cartoons",
            "type/video+tone/comment"
        ),
        arrayOf(
            "football",
            "sport/cricket",
            "sport/rugby-union",
            "sport/tennis",
            "sport/cycling",
            "sport/formulaone",
            "sport/golf",
            "sport/us-sport"
        ),
        arrayOf(
            "books",
            "music",
            "uk/tv-and-radio",
            "artanddesign",
            "uk/film",
            "games",
            "music/classical-music-and-opera",
            "stage"
        ),
        arrayOf(
            "fashion",
            "food",
            "tone/recipes",
            "lifeandstyle/love-and-sex",
            "lifeandstyle/health-and-wellbeing",
            "lifeandstyle/home-and-garden",
            "lifeandstyle/women",
            "lifeandstyle/men",
            "lifeandstyle/family",
            "uk/travel",
            "uk/money"
        )
    )

    override suspend fun loadRemote(category:Int,subcategory:Int): List<ItemDto> {
        try {
            postDao.clearAllNews(category,subcategory)
            val route = subCategoriesRoutes[category][subcategory]
            val channel = rssFeed.getRss(route).channel
            return channel.items.map {
                val i = it.copy(
                    description = it.description.removeHtmlTags(),
                    pubDate = it.pubDate.formatDate()
                )
                saveToLocal(i,category,subcategory)
                return@map i
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun saveToLocal(item: ItemDto,category:Int,subcategory:Int) {
        postDao.insertFullNews(converter.toEntity(item,category,subcategory))
    }

    override suspend fun loadLocalSavedNews(category:Int,subcategory:Int): List<ItemDto> {
        return postDao.getAllNewsWithRelations(category,subcategory).map {
            converter.toDto(it)
        }
    }

    suspend fun loadAll(){
        for(c in subCategoriesRoutes.indices){
            for(sc in subCategoriesRoutes[c].indices){
                loadRemote(c,sc)
            }
        }
    }
}
