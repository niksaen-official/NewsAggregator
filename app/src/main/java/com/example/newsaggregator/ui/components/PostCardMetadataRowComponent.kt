package com.example.newsaggregator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.R

@Composable
fun PostCardMetadataRow(publishedData: String,creator: String){
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