package com.miso.vinilos.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.R
import com.miso.vinilos.models.Album
import com.miso.vinilos.ui.composables.ListDivider
import com.miso.vinilos.ui.theme.VinilosTheme


@Composable
fun AlbumListScreen(
    albums: List<Album>,
    fetchAlbums: () -> Unit,
    isLoading: Boolean,
    navigateToAlbumDetail: (albumId: Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val listDescription = stringResource(R.string.lista_albumes_descripcion)

    LaunchedEffect(Unit) {
        fetchAlbums()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .testTag("AlbumList")
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics { contentDescription = listDescription }
        ) {
            items(albums) { album ->
                AlbumItem(
                    album = album,
                    onNavigateToAlbumDetail = navigateToAlbumDetail
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun AlbumItem(
    album: Album,
    onNavigateToAlbumDetail: (albumId: Int) -> Unit,
) {
    ListDivider()
    ListItem(

        modifier = Modifier.clickable { onNavigateToAlbumDetail(album.id) },
        overlineContent = {
            Text(
                text = album.performers.getOrNull(0)?.name ?: stringResource(R.string.sin_artista)
            )
        },
        headlineContent = {
            Text(
                text = album.name
            )
        },

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
                cover = "https://placehold.co/400x400.png",
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
            albums = listOf(
                Album(
                    id = 1,
                    name = "The Dark Side of the Moon",
                    releaseDate = "1973",
                    cover = "https://placehold.co/400x400.png",
                    description = "The Dark Side of the Moon es el octavo álbum de estudio de la banda británica de rock progresivo Pink Floyd, lanzado el 1 de marzo de 1973.",
                    comments = emptyList(),
                    performers = emptyList(),
                    genre = "Rock progresivo",
                    recordLabel = "Harvest Records"
                ),
                Album(
                    id = 2,
                    name = "The Wall",
                    releaseDate = "1979",
                    cover = "https://placehold.co/400x400.png",
                    description = "The Wall es el undécimo álbum de estudio de la banda británica de rock Pink Floyd, lanzado el 30 de noviembre de 1979.",
                    comments = emptyList(),
                    performers = emptyList(),
                    genre = "Rock",
                    recordLabel = "Harvest Records"
                ),
            ),
            isLoading = false,
            fetchAlbums = {}
        )
    }
}