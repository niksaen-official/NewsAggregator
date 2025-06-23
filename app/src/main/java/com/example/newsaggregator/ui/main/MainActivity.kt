package com.example.newsaggregator.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.imageLoader
import com.example.newsaggregator.ui.components.Header
import com.example.newsaggregator.ui.components.LoadingScreen
import com.example.newsaggregator.ui.components.PostCardItem
import com.example.newsaggregator.ui.dialogs.WarningDialog
import com.example.newsaggregator.ui.theme.NewsAggregatorTheme
import com.example.newsaggregator.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vm.loadNews()
        if(vm.isOnline){
            val imageLoader = this.imageLoader
            imageLoader.diskCache?.clear()
            imageLoader.memoryCache?.clear()
        }
        setContent {
            NewsAggregatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(innerPadding)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Main(paddingValues: PaddingValues){
        var showWarningDialog by remember { mutableStateOf(true) }
        val news by vm.posts.collectAsStateWithLifecycle()
        val category by vm.category.collectAsStateWithLifecycle()
        val subCategory by vm.subCategory.collectAsStateWithLifecycle()
        val isLoading by vm.isLoading.collectAsStateWithLifecycle()
        val isReloading by vm.isReloading.collectAsStateWithLifecycle()
        val state = rememberPullToRefreshState()

        if (!vm.isOnline && showWarningDialog) {
            WarningDialog(
                onConfirm = {showWarningDialog = false},
                onDismiss = {showWarningDialog = false;finish()}
            )
        }
        PullToRefreshBox(
            isRefreshing = isReloading,
            onRefresh = { vm.reloadNews() },
            state = state,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isReloading,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = state
                )
            },
        ) {
            Column(modifier = Modifier.padding(paddingValues)) {
                Header(
                    onSearchBtnClick = { vm.search(it) },
                    onCategoryChanged = { vm.changeCategory(it) },
                    changedCategory = category,
                    onSubCategoryChanged = { vm.changeSubCategory(it) },
                    changedSubCategory = subCategory
                )
                if(!isLoading){
                    LazyColumn {
                        items(news.size) {
                            PostCardItem(news[it],vm.isOnline){
                                val intent = Intent(this@MainActivity, WebActivity::class.java)
                                intent.putExtra("url",it)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
                else{
                    LoadingScreen(paddingValues)
                }
            }
        }
    }
}

