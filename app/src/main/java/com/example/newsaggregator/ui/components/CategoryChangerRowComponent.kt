package com.example.newsaggregator.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import com.example.newsaggregator.R

@Composable
fun CategoryChangerRow(onCategoryChanged:(Int)->Unit,changedCategory:Int){
    val categories = stringArrayResource(R.array.categories)
    LazyRow{
        items(categories.size) {
            CategoryItem(categories[it],changedCategory==it) { onCategoryChanged.invoke(it) }
        }
    }
}
