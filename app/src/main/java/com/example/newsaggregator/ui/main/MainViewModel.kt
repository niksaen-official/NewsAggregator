package com.example.newsaggregator.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.data.network.NetworkStatusChecker
import com.example.newsaggregator.data.repository.NewsLoaderRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsLoaderRepository: NewsLoaderRepositoryImpl,
    private val networkStatusChecker: NetworkStatusChecker
) : ViewModel(){

    private val _news = MutableStateFlow<List<ItemDto>>(emptyList())
    private val _isLoadingState = MutableStateFlow<Boolean>(true)
    private var originalNews: List<ItemDto> = emptyList()

    val news: StateFlow<List<ItemDto>> = _news
    val isLoading: StateFlow<Boolean> = _isLoadingState
    val isOnline get() = networkStatusChecker.isOnline()

    fun loadNews(){
        viewModelScope.launch {
            originalNews =
                if(isOnline) newsLoaderRepository.loadRemote()
                else newsLoaderRepository.loadLocalSavedNews()
            _isLoadingState.value = false
            _news.value = originalNews
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _news.value = originalNews
        } else {
            _news.value = originalNews.filter { item ->
                item.title.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
            }
        }
    }
}