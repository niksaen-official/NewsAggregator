package com.niksaengames.newsaggregator.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niksaengames.newsaggregator.data.network.NetworkStatusChecker
import com.niksaengames.newsaggregator.data.remote.dto.ItemDto
import com.niksaengames.newsaggregator.data.repository.PostLoaderRepositoryImpl
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
    private val _isReloadingState = MutableStateFlow<Boolean>(false)
    private val _category = MutableStateFlow<Int>(0)
    private val _subCategory = MutableStateFlow<Int>(0)
    private var originalPosts: List<ItemDto> = emptyList()
    private var query: String = ""

    val posts: StateFlow<List<ItemDto>> = _posts

    val isLoading: StateFlow<Boolean> = _isLoadingState
    val isReloading: StateFlow<Boolean> = _isReloadingState
    val category: StateFlow<Int> = _category
    val subCategory: StateFlow<Int> = _subCategory
    val isOnline get() = networkStatusChecker.isOnline()

    fun loadNews(){
        _isLoadingState.value = true
        viewModelScope.launch {
            originalPosts =
                if(isOnline) postLoaderRepository.loadRemote(category.value,subCategory.value)
                else postLoaderRepository.loadLocalSavedNews(category.value,subCategory.value)
            _posts.value = originalPosts
            if(query.isNotEmpty()) search(query)
            _isLoadingState.value = false
        }
    }


    fun reloadNews(){
        _isReloadingState.value = true
        viewModelScope.launch {
            originalPosts =
                if(isOnline) postLoaderRepository.loadRemote(category.value,subCategory.value)
                else postLoaderRepository.loadLocalSavedNews(category.value,subCategory.value)
            _posts.value = originalPosts
            if(query.isNotEmpty()) search(query)
            _isReloadingState.value = false
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _posts.value = originalPosts
        } else {
            this.query = query
            _posts.value = originalPosts.filter { item ->
                item.title.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
            }
        }
    }

    fun changeCategory(category: Int){
        _category.value = category
        _subCategory.value = 0
        loadNews()
    }

    fun changeSubCategory(sub:Int){
        _subCategory.value = sub
        loadNews()
    }
}