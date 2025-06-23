package com.niksaengames.newsaggregator.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niksaengames.newsaggregator.R

@Composable
fun Header(
    onSearchBtnClick:(text:String)-> Unit,
    onCategoryChanged:(Int)->Unit,
    changedCategory: Int,
    onSubCategoryChanged:(Int)->Unit,
    changedSubCategory:Int){

    val categories = stringArrayResource(R.array.categories)
    val subCategories = when(changedCategory){
        0 -> stringArrayResource(R.array.news)
        1 -> stringArrayResource(R.array.opinion)
        2 -> stringArrayResource(R.array.sport)
        3 -> stringArrayResource(R.array.culture)
        4 -> stringArrayResource(R.array.lifestyle)
        else -> stringArrayResource(R.array.news)
    }
    Column (modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        SearchRow { onSearchBtnClick.invoke(it) }
        Spacer(modifier = Modifier.height(8.dp))
        CategoryChangerRow(onCategoryChanged,changedCategory,categories)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryChangerRow(onSubCategoryChanged,changedSubCategory, subCategories)
    }
}

