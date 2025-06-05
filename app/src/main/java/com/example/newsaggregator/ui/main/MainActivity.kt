package com.example.newsaggregator.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import com.example.newsaggregator.R
import com.example.newsaggregator.data.remote.Commentisfree
import com.example.newsaggregator.data.remote.Culture
import com.example.newsaggregator.data.remote.International
import com.example.newsaggregator.data.remote.LifeAndStyle
import com.example.newsaggregator.data.remote.Sport
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.ui.components.Header
import com.example.newsaggregator.ui.components.LoadingScreen
import com.example.newsaggregator.ui.components.PostCardItem
import com.example.newsaggregator.ui.components.PostCardMetadataRow
import com.example.newsaggregator.ui.dialogs.ErrorDialog
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
        vm.loadNews(International)
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

    @Composable
    fun Main(paddingValues: PaddingValues){
        var showWarningDialog = remember { mutableStateOf(true) }

        if (!vm.isOnline && showWarningDialog.value) {
            WarningDialog(
                onConfirm = {showWarningDialog.value = false},
                onDismiss = {showWarningDialog.value = false;finish()}
            )
        }

        val news = vm.posts.collectAsStateWithLifecycle()
        val currentCategory = vm.currentCategory.collectAsStateWithLifecycle()
        val isLoading = vm.isLoading.collectAsStateWithLifecycle()
        Column(modifier = Modifier.padding(paddingValues)) {
            Header(
                onSearchBtnClick = { vm.search(it) },
                onCategoryChanged = { vm.categoryChanged(it) },
                currentCategory.value
            )
            if(!isLoading.value){
                LazyColumn {
                    items(news.value.size) {
                        PostCardItem(news.value[it],vm.isOnline){
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

