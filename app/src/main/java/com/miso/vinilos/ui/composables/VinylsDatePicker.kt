package com.miso.vinilos.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.miso.vinilos.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun VynilsDatePicker(
    state: MutableState<String>,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "DD/MM/AAAA",
    error: String? = null,
    forceShowError: Boolean = false,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    VinylsTextField(
        state = state,
        label = label,
        placeholder = placeholder,
        modifier = modifier,
        error = error,
        onClick = { showDatePicker = true },
        readOnly = true,
        forceShowError = forceShowError,
        trailingIcon = null,
        leadingIcon = {
            Icon(
                Icons.Outlined.Today,
                contentDescription = stringResource(R.string.seleccionar_fecha),
                modifier = Modifier.clickable {
                    showDatePicker = true
                }
            )
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.value = dateState.selectedDateMillis?.let { formatDate(it) } ?: ""
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.aceptar))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancelar))
                }
            }
        ) {
            DatePicker(
                state = dateState,
                showModeToggle = false,
                title = null,
                headline = null,
            )
        }
    }
}

fun formatDate(milliseconds: Long): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}