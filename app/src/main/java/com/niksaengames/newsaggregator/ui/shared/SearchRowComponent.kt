package com.niksaengames.newsaggregator.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.niksaengames.newsaggregator.R

@Composable
fun SearchRow(onSearchBtnClick:(inputText: String)->Unit){
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
                onSearchBtnClick.invoke(text.value)
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