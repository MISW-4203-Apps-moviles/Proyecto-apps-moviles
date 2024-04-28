package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.viewModels.AlbumDetailViewModel

@Composable
fun AlbumDetailRoute(
    viewModel: AlbumDetailViewModel,
    albumId: String,
    innerPadding: PaddingValues = PaddingValues()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val album by viewModel.album.observeAsState(null)

    AlbumDetailScreen(
        innerPadding = innerPadding,
        isLoading = isLoading,
        album = album,
        fetchAlbum = { viewModel.fetchAlbum(albumId.toInt()) }
    )
}