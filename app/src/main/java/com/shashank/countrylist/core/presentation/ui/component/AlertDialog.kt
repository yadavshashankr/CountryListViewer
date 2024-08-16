package com.shashank.countrylist.core.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties

/**
 * 'AlertDialog' is a generic Dialog that helps notifying user about events and is also configured to handle
 * consent based processes.
 */

@Composable
fun AlertDialog(
    titleText: String,
    messageText: String,
    buttonPositiveText: String?,
    buttonNegativeText: String?,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {
    Column {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {

            AlertDialog(onDismissRequest = {
                openDialog.value = true
            }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true
            ), title = {
                Text(text = titleText)
            }, text = {
                Text(messageText)
            }, confirmButton = {
                if (buttonPositiveText != null) {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            onPositiveClick()
                        }) {
                        Text(buttonPositiveText)
                    }
                }
            }, dismissButton = {
                if (buttonNegativeText != null) {
                    Button(onClick = {
                        openDialog.value = false
                        onNegativeClick()
                    }) {
                        Text(buttonNegativeText)
                    }
                }
            })
        }
    }
}