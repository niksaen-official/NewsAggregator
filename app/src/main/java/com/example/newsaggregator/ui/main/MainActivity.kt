package com.example.newsaggregator.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import com.example.newsaggregator.R
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.ui.components.Header
import com.example.newsaggregator.ui.components.LoadingScreen
import com.example.newsaggregator.ui.components.NewsCardMetadataRow
import com.example.newsaggregator.ui.components.SearchRow
import com.example.newsaggregator.ui.dialogs.ErrorDialog
import com.example.newsaggregator.ui.dialogs.WarningDialog
import com.example.newsaggregator.ui.theme.NewsAggregatorTheme
import com.example.newsaggregator.ui.web.WebActivity
import com.google.android.material.snackbar.Snackbar
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

    @Composable
    fun Main(paddingValues: PaddingValues){
        var showWarningDialog = remember { mutableStateOf(true) }

        if (!vm.isOnline && showWarningDialog.value) {
            WarningDialog(
                onConfirm = {showWarningDialog.value = false},
                onDismiss = {showWarningDialog.value = false}
            )
        }

        val news = vm.news.collectAsStateWithLifecycle()
        val isLoading = vm.isLoading.collectAsStateWithLifecycle()
        if(!isLoading.value){
            LazyColumn(contentPadding = paddingValues) {
                item {
                    Header({vm.search(it)})
                }
                items(news.value.size) {
                    NewsCardItem(news.value[it])
                }
            }
        }
        else{
            LoadingScreen(paddingValues)
        }
    }

    @Composable
    fun NewsCardItem(news: ItemDto){
        var showErrorDialog = remember { mutableStateOf(false) }

        if (showErrorDialog.value) {
            ErrorDialog(
                onDismiss = {showErrorDialog.value = false},
                onConfirm = {showErrorDialog.value = false}
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        if(vm.isOnline){
                            val intent = Intent(this@MainActivity, WebActivity::class.java)
                            intent.putExtra("url",news.guid)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            showErrorDialog.value = true
                        }
                    }),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 4
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = news.contents[0].url,
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.error_image)
                    ),
                    modifier = Modifier
                        .height(news.contents[0].width?.toInt()?.dp ?: 40.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 8
                )
                Spacer(modifier = Modifier.height(8.dp))
                NewsCardMetadataRow(news.pubDate,news.dcCreator)
            }
        }
    }
}

