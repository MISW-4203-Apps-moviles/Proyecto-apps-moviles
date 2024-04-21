package com.miso.vinilos.ui.composables

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListDivider(color: Color? = null) {
    val dividerColor = color ?: MaterialTheme.colorScheme.primaryContainer
    HorizontalDivider(color = dividerColor, thickness = 1.dp)
}