package com.miso.vinilos.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun AlbumDetailScreen(
    innerPadding: PaddingValues = PaddingValues(),
    albumId: String?
) {
    Text(text = "$albumId", modifier = Modifier.padding(innerPadding))
}
