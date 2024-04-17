package com.miso.vinilos.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.miso.vinilos.viewModels.AlbumDetailViewModel


@Composable
fun AlbumDetailScreen(
    viewModel: AlbumDetailViewModel,
    innerPadding: PaddingValues = PaddingValues(),
    albumId: String
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val album by viewModel.album.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchAlbum(albumId.toInt())
    }

    if (isLoading || album == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    album?.let { Text(text = it.name, modifier = Modifier.padding(innerPadding)) }
}
