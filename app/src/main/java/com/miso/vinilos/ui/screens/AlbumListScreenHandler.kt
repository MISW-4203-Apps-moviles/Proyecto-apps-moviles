package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.ui.composables.ErrorScreen
import com.miso.vinilos.ui.composables.SpinnerScreen
import com.miso.vinilos.viewModels.AlbumListViewModel

@Composable
fun AlbumListScreenHandler(
    vinylUiState: VinylUiState,
    retryAction: () -> Unit,
    viewModel: AlbumListViewModel,
    navigateToAlbumDetail: (albumId: Int) -> Unit,
    navigateToAlbumCreate: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val albums by viewModel.albums.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchAlbums()
    }

    when (vinylUiState) {
        is VinylUiState.Loading -> SpinnerScreen()
        is VinylUiState.Success -> AlbumListScreen(
            navigateToAlbumDetail = navigateToAlbumDetail,
            navigateToAlbumCreate = navigateToAlbumCreate,
            innerPadding = innerPadding,
            albums = albums,
        )
        is VinylUiState.Error -> ErrorScreen(retryAction)
    }
}