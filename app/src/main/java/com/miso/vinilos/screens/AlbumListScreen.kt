package com.miso.vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.models.Album
import com.miso.vinilos.ui.theme.VinilosTheme
import com.miso.vinilos.viewModels.AlbumListViewModel


@Composable
fun AlbumListScreen(
    viewModel: AlbumListViewModel,
    innerPadding: PaddingValues = PaddingValues(),
    navigateToAlbumDetail: (albumId: Int) -> Unit
) {
    val albums by viewModel.albums.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAlbums()
    }

    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        items(albums) { album ->
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
    onNavigateToAlbumDetail: (albumId: Int) -> Unit
) {
    println(album)
    ListItem(
        modifier = Modifier.clickable { onNavigateToAlbumDetail(album.id) },
        overlineContent = { Text(album.performers[0].name) },
        headlineContent = { Text(album.name) },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = album.cover)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Portada de ${album.name}",
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
                id = 1,
                name = "The Dark Side of the Moon",
                releaseDate = "1973",
                cover = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k",
                description = "The Dark Side of the Moon es el octavo álbum de estudio de la banda británica de rock progresivo Pink Floyd, lanzado el 1 de marzo de 1973.",
                comments = emptyList(),
                performers = emptyList(),
                genre = "Rock progresivo",
                recordLabel = "Harvest Records"
            ),
            onNavigateToAlbumDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumListScreenPreview() {
    VinilosTheme {
        AlbumListScreen(
            navigateToAlbumDetail = {},
            viewModel = AlbumListViewModel()
        )
    }
}