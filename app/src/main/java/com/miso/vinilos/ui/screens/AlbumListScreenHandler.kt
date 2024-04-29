package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.viewModels.AlbumListViewModel

@Composable
fun AlbumListScreenHandler(
    viewModel: AlbumListViewModel,
    navigateToAlbumDetail: (albumId: Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val albums by viewModel.albums.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    AlbumListScreen(
        navigateToAlbumDetail = navigateToAlbumDetail,
        innerPadding = innerPadding,
        albums = albums,
        isLoading = isLoading,
        fetchAlbums = { viewModel.fetchAlbums() }
    )
}