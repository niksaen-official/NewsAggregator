package com.example.newsaggregator.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.newsaggregator.R
import com.example.newsaggregator.data.remote.dto.ItemDto
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
        val news = vm.news.collectAsStateWithLifecycle()
        val isLoading = vm.isLoading.collectAsStateWithLifecycle()
        if(!isLoading.value){
            LazyColumn(contentPadding = paddingValues) {
                item {
                    Header()
                }
                items(news.value.size) {
                    NewsCardItem(news.value[it])
                }
            }
        }
        else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    Header()
                }
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    @Composable
    fun NewsCardItem(news: ItemDto){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        val intent = Intent(this@MainActivity, WebActivity::class.java)
                        intent.putExtra("url",news.guid)
                        startActivity(intent)
                        finish()
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

    @Composable
    fun NewsCardMetadataRow(publishedData: String,creator: String){
        Row(modifier = Modifier.fillMaxWidth()) {
            if(creator.isNotEmpty()){
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.inversePrimary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .weight(1f),
                    text = stringResource(R.string.creator, creator),
                    style = MaterialTheme.typography.bodySmall
                )

            }
            else{
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .weight(1f),
                text = stringResource(R.string.published, publishedData),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    @Composable
    fun Header(){
        Column (modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            SearchRow()
        }
    }

    @Composable
    fun SearchRow(){
        var text = remember { mutableStateOf("") }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = text.value,
                onValueChange = {text.value = it},
                modifier = Modifier.weight(1f),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                placeholder = {Text("Search:")},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),

            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    vm.search(text.value)
                },
                modifier = Modifier.size(56.dp).align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier.scale(3f),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}