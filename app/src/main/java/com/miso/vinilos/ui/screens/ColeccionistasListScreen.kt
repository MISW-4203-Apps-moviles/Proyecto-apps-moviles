package com.miso.vinilos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.miso.vinilos.models.Collector
import com.miso.vinilos.ui.composables.ListDivider
import com.miso.vinilos.ui.theme.VinylsTheme


@Composable
fun ColeccionistasListScreen(
    collections: List<Collector>,
    navigateToCollectionDetail: (collectionId: Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val listDescription = stringResource(R.string.lista_coleccionistas_descripcion)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics { contentDescription = listDescription }
                .testTag("ItemCollectionList"),
        ) {
            items(collections, key = { album -> album.id }) { collection ->
                CollectionItem(
                    collection = collection,
                    onNavigateToCollectionDetail = navigateToCollectionDetail
                )
            }
        }
    }
}

@Composable
fun CollectionItem(
    collection: Collector,
    onNavigateToCollectionDetail: (collectionId: Int) -> Unit,
) {

    val collectorNameDescription = stringResource(R.string.nombre_del_coleccionista)

    ListDivider()
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToCollectionDetail(collection.id) }
            .semantics { contentDescription = collectorNameDescription },
        headlineContent = {
            Text(
                text = collection.name

            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = stringResource(R.string.ver_detalles_de_coleccionista)
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun CollectionItemPreview() {
    VinylsTheme(darkTheme = true) {
        CollectionItem(
            collection = Collector(
                id = 1,
                name = "Manolo Bellon",
                telephone = "12334324432",
                email = "test@test.com",
                comments = emptyList(),
                favoritePerformers = emptyList(),
                collectorAlbums = emptyList()
            ),
            onNavigateToCollectionDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColeccionistasListScreenPreview() {
    VinylsTheme (darkTheme = true) {
        ColeccionistasListScreen(
            navigateToCollectionDetail = {},
            collections = listOf(
                Collector(
                    id = 1,
                    name = "Manolo Bellon",
                    telephone = "12334324432",
                    email = "test@test.com",
                    comments = emptyList(),
                    favoritePerformers = emptyList(),
                    collectorAlbums = emptyList()

                ),
                Collector(
                    id = 1,
                    name = "Juan Bellon",
                    telephone = "12334322222",
                    email = "test2@test.com",
                    comments = emptyList(),
                    favoritePerformers = emptyList(),
                    collectorAlbums = emptyList()
                ),
            ),
        )
    }
}