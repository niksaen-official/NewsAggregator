package com.example.newsaggregator.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.data.network.NetworkStatusChecker
import com.example.newsaggregator.data.remote.Commentisfree
import com.example.newsaggregator.data.remote.Culture
import com.example.newsaggregator.data.remote.International
import com.example.newsaggregator.data.remote.LifeAndStyle
import com.example.newsaggregator.data.remote.Sport
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.data.repository.PostLoaderRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postLoaderRepository: PostLoaderRepositoryImpl,
    private val networkStatusChecker: NetworkStatusChecker
) : ViewModel(){

    private val _posts = MutableStateFlow<List<ItemDto>>(emptyList())
    private val _isLoadingState = MutableStateFlow<Boolean>(true)
    private val _currentCategory = MutableStateFlow<Int>(0)
    private var originalPosts: List<ItemDto> = emptyList()

    val posts: StateFlow<List<ItemDto>> = _posts
    val isLoading: StateFlow<Boolean> = _isLoadingState
    val currentCategory: StateFlow<Int> = _currentCategory
    val isOnline get() = networkStatusChecker.isOnline()

    fun loadNews(category: String){
        _isLoadingState.value = true
        viewModelScope.launch {
            originalPosts =
                if(isOnline) postLoaderRepository.loadRemote(category)
                else postLoaderRepository.loadLocalSavedNews(category)
            _isLoadingState.value = false
            _posts.value = originalPosts
            Log.d("MainViewModel",originalPosts.toString())
        }
    }

    fun categoryChanged(pos:Int){
        _currentCategory.value = pos
        when(pos){
            0->loadNews(International)
            1->loadNews(Commentisfree)
            2->loadNews(Sport)
            3->loadNews(Culture)
            4->loadNews(LifeAndStyle)
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _posts.value = originalPosts
        } else {
            _posts.value = originalPosts.filter { item ->
                item.title.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
            }
        }
    }
}