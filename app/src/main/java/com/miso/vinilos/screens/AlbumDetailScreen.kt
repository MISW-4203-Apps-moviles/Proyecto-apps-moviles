package com.miso.vinilos.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import com.miso.vinilos.viewModels.AlbumDetailViewModel


@Composable
fun AlbumDetailScreen(
    viewModel: AlbumDetailViewModel,
    albumId: String,
    innerPadding: PaddingValues = PaddingValues()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val album by viewModel.album.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchAlbum(albumId.toInt())
    }

    if (isLoading || album == null) {
        Box(
            modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    album?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(text = it.name, modifier = Modifier.padding(innerPadding))
        }
    }
}
