package com.miso.vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.screens.viewModels.Album
import com.miso.vinilos.screens.viewModels.AlbumListViewModel
import com.miso.vinilos.ui.theme.VinilosTheme


@Composable
fun AlbumesScreen(
    viewModel: AlbumListViewModel = viewModel(),
    innerPadding: PaddingValues = PaddingValues(),
    navigateToAlbumDetail: (albumId: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        items(uiState.albums) { album ->
            AlbumItem(
                album = album,
                onNavigateToAlbumDetail = navigateToAlbumDetail
            )
        }
    }
}

@Composable
fun AlbumItem(
    album: Album,
    onNavigateToAlbumDetail: (albumId: String) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onNavigateToAlbumDetail(album.id) },
        overlineContent = { Text(album.artist) },
        headlineContent = { Text(album.title) },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = album.imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Portada de ${album.title}",
                modifier = Modifier.size(48.dp),
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AlbumItemPreview() {
    VinilosTheme {
        AlbumItem(
            album = Album(
                id = "1",
                title = "The Dark Side of the Moon",
                artist = "Pink Floyd",
                year = "1973",
                imageUrl = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k"
            ),
            onNavigateToAlbumDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumesScreenPreview() {
    VinilosTheme {
        AlbumesScreen(
            navigateToAlbumDetail = {}
        )
    }
}