package com.miso.vinilos.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import com.miso.vinilos.R

@Composable
fun VinylsTextField(
    state: MutableState<String>,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    forceShowError: Boolean = false,
    trailingIcon: (@Composable (() -> Unit))? = {
        Icon(
            Icons.Outlined.Cancel,
            contentDescription = stringResource(R.string.limpiar_campo_texto),
            modifier = Modifier.clickable {
                state.value = ""
            }
        )
    },
    leadingIcon: (@Composable (() -> Unit))? = null,
    onClick: (() -> Unit)? = null,
) {

    var wasFocused by remember { mutableStateOf(false) }
    var focusTime = 0

    TextField(
        singleLine = singleLine,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(label) },
        isError = error != null && (wasFocused || forceShowError),
        enabled = enabled,
        supportingText = if (error == null || !(wasFocused || forceShowError)) null else error.let { { Text(it) } },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (!focusState.isFocused) {
                    if (focusTime > 0) {
                        wasFocused = true
                    } else {
                        focusTime++
                    }
                }
            },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick?.invoke()
                        }
                    }
                }
            },
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
            focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
            errorContainerColor = MaterialTheme.colorScheme.onSecondary

        )
    )
}