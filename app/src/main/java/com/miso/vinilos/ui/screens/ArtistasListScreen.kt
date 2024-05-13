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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.R
import com.miso.vinilos.models.Performer
import com.miso.vinilos.ui.composables.ListDivider
import com.miso.vinilos.ui.theme.VinylsTheme


@Composable
fun ArtistasListScreen(
    performers: List<Performer>,
    navigateToPerformerDetail: (performerId: String) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val listDescription = stringResource(R.string.lista_artistas_descripcion)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics { contentDescription = listDescription }

        ) {
            items(performers, key = { performer -> performer.id }) { performer ->
                PerformerItem(
                    performer = performer,
                    onNavigateToPerformerDetail = navigateToPerformerDetail
                )
            }
        }
    }
}

@Composable
fun PerformerItem(
    performer: Performer,
    onNavigateToPerformerDetail: (performerId: String) -> Unit,
) {

    val performerDescription = stringResource(R.string.album_artista_descripcion)
    val performerContent = stringResource(R.string.performer_click)

    ListDivider()
    ListItem(
        modifier = Modifier
            .clickable { onNavigateToPerformerDetail(performer.id) }
            .semantics { contentDescription = performerDescription },
        headlineContent = {
            Text(
                text = performer.name,
                modifier = Modifier.semantics { contentDescription = performerContent }
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = stringResource(R.string.detalles_del_artista)
            )
        },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = performer.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Imagen de ${performer.name}",
                modifier = Modifier.size(48.dp),
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PerformerItemPreview() {
    VinylsTheme(darkTheme = true) {
       PerformerItem(
            Performer(
                    id = "1",
                    name = "Joan Manuel Serrat Teresa",
                    image = "https://upload.wikimedia.org/wikipedia/commons/e/e3/Serrat.jpg",
                    description = "Es un cantautor, compositor, actor, escritor, poeta y músico español.",
                    birthDate = "1943-12-27T00:00:00-05:00",
                    albums = emptyList(),
                ),
            onNavigateToPerformerDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistasListScreenPreview() {
    VinylsTheme (darkTheme = true) {
        ArtistasListScreen(
            navigateToPerformerDetail = {},
            performers = listOf(
                Performer(
                    id = "1",
                    name = "Joan Manuel Serrat Teresa",
                    image = "https://upload.wikimedia.org/wikipedia/commons/e/e3/Serrat.jpg",
                    description = "Es un cantautor, compositor, actor, escritor, poeta y músico español.",
                    birthDate = "1943-12-27T00:00:00-05:00",
                    albums = emptyList(),
                ),
                Performer(
                    id = "2",
                    name = "Rubén Blades Bellido de Luna",
                    image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
                    description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
                    birthDate = "1948-07-16T05:00:00.000Z",
                    albums = emptyList(),
                ),
            ),
        )
    }
}