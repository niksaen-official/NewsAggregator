package com.example.newsaggregator.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil3.compose.rememberAsyncImagePainter
import com.example.newsaggregator.R
import com.example.newsaggregator.data.remote.dto.ItemDto
import com.example.newsaggregator.ui.dialogs.ErrorDialog
import com.example.newsaggregator.ui.main.MainActivity
import com.example.newsaggregator.ui.web.WebActivity

@Composable
fun PostCardItem(news: ItemDto,isOnline: Boolean,onItemClicked:(url: String)->Unit){
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
                    if(isOnline){
                        onItemClicked.invoke(news.guid)
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
            PostCardMetadataRow(news.pubDate,news.dcCreator)
        }
    }
}