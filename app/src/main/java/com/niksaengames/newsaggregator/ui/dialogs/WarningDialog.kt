package com.niksaengames.newsaggregator.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.niksaengames.newsaggregator.R

@Composable
fun WarningDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.warning)) },
        text = { Text(stringResource(R.string.internet_connection_is_not_available)) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(stringResource(R.string.continue_text))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.exit))
            }
        }
    )
}