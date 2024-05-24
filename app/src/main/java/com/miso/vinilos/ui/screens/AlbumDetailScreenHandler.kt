package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.viewModels.AlbumDetailViewModel

@Composable
fun AlbumDetailScreenHandler(
    viewModel: AlbumDetailViewModel,
    albumId: String,
    innerPadding: PaddingValues = PaddingValues()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val album by viewModel.album.observeAsState(null)
    val isOpenDialog by viewModel.isOpenDialog.observeAsState(false)

    AlbumDetailScreen(
        innerPadding = innerPadding,
        isLoading = isLoading,
        album = album,
        isOpenDialog = isOpenDialog,
        toggleCommentModal = { viewModel.toggleCommentModal() },
        fetchAlbum = { viewModel.fetchAlbum(albumId.toInt()) },
        comments = album?.comments.orEmpty(),
        onCommentAdded = { comment -> viewModel.postComment(albumId.toInt(), comment) }
    )
}