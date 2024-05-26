package com.miso.vinilos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.miso.vinilos.ui.theme.VinylsTheme
import com.miso.vinilos.ui.theme.primaryDark

const val COVER_URL = "https://placehold.co/400x400.png"
const val RECORD_LABEL = "Harvest Records"

@Composable
fun AlbumListScreen(
    albums: List<Album>,
    navigateToAlbumDetail: (albumId: Int) -> Unit,
    navigateToAlbumCreate: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val listDescription = stringResource(R.string.lista_albumes_descripcion)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics { contentDescription = listDescription }
                .testTag("ItemAlbum")
                .weight(10f)
        ) {
            items(albums, key = { album -> album.id!! }) { album ->
                AlbumItem(
                    album = album,
                    onNavigateToAlbumDetail = navigateToAlbumDetail
                )
            }
        }

        ExtendedFloatingActionButton(
            onClick = {navigateToAlbumCreate()},
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.agregar_album)) },
            text = { Text(text = stringResource(R.string.agregar_album)) },
            containerColor = primaryDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp,
                    top = 20.dp,
                )
        )
    }
}


@Composable
fun AlbumItem(
    album: Album,
    onNavigateToAlbumDetail: (albumId: Int) -> Unit,
) {

    val nombreAlbumDescripcion = stringResource(R.string.album_nombre_descripcion)

    ListDivider()
    ListItem(

        modifier = Modifier
            .clickable { album.id?.let { onNavigateToAlbumDetail(it) } },

        overlineContent = {
            Text(
                text = album.performers?.getOrNull(0)?.name ?: stringResource(R.string.sin_artista)
            )
        },
        headlineContent = {
            Text(
                text = album.name,
                modifier = Modifier.semantics { contentDescription = nombreAlbumDescripcion }
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
    VinylsTheme(darkTheme = true) {
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
                recordLabel = RECORD_LABEL
            ),
            onNavigateToAlbumDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumListScreenPreview() {

    VinylsTheme (darkTheme = true) {
        AlbumListScreen(
            navigateToAlbumDetail = {},
            navigateToAlbumCreate = {},
            albums =
            listOf(
                Album(
                    id = 1,
                    name = "The Dark Side of the Moon",
                    releaseDate = "1973",
                    cover = COVER_URL,
                    description = "The Dark Side of the Moon es el octavo álbum de estudio de la banda británica de rock progresivo Pink Floyd, lanzado el 1 de marzo de 1973.",
                    comments = emptyList(),
                    performers = emptyList(),
                    genre = "Rock progresivo",
                    recordLabel = RECORD_LABEL
                ),
                Album(
                    id = 2,
                    name = "The Wall",
                    releaseDate = "1979",
                    cover = COVER_URL,
                    description = "The Wall es el undécimo álbum de estudio de la banda británica de rock Pink Floyd, lanzado el 30 de noviembre de 1979.",
                    comments = emptyList(),
                    performers = emptyList(),
                    genre = "Rock",
                    recordLabel = RECORD_LABEL
                ),
            ),
        )
    }
}