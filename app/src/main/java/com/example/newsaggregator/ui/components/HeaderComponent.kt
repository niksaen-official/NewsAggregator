package com.example.newsaggregator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.R

@Composable
fun Header(onSearchBtnClick:(text:String)-> Unit){
    Column (modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        SearchRow { onSearchBtnClick.invoke(it) }
    }
}