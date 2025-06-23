package com.niksaengames.newsaggregator.ui.shared

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable

@Composable
fun CategoryChangerRow(onCategoryChanged:(Int)->Unit,changedCategory:Int,categories: Array<String>){
    LazyRow{
        items(categories.size) {
            CategoryItem(categories[it],changedCategory==it) { onCategoryChanged.invoke(it) }
        }
    }
}
