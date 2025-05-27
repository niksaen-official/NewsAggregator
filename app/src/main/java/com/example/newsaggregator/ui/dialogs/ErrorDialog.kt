package com.example.newsaggregator.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.newsaggregator.R

@Composable
fun ErrorDialog(onDismiss: () -> Unit,
                onConfirm: () -> Unit){
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.errorContainer,
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.error)) },
        text = { Text(stringResource(R.string.to_continue_you_need_an_internet_connection)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                onClick = onConfirm
            ) {
                Text(stringResource(R.string.go_back))
            }
        }
    )
}