package com.miso.vinilos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.R
import com.miso.vinilos.models.Performer

@Composable
fun ArtistaDetailScreen(
    performer: Performer?,
    isLoading: Boolean,
    fetchPerformer: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val loadingDescription = stringResource(R.string.cargando_artista_description)

    LaunchedEffect(Unit) {
        fetchPerformer()
    }

    if (isLoading || performer == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.semantics { contentDescription = loadingDescription }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
    ) {

        performer?.let {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                ArtistaCard(
                    performer = it,
                )

               /* AlbumsSection(
                    onAddComment = {}
                )*/
            }
        }
    }
}

@Composable
fun ArtistaCard(
    performer: Performer,
) {
    val nombreArtistaDescripcion = stringResource(R.string.artista_nombre)
    val imagenArtistaDescripcion = stringResource(R.string.artista_imagen_descripcion)
    val descripcionArtista = stringResource(R.string.artista_descripcion)
    val fechaNacimientoArtista = stringResource(R.string.artista_fecha_nacimiento)
    val albumesArtista = "Albumes pertenecientes al artista"

    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = contentColorFor(MaterialTheme.colorScheme.onSurface)
        )
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = performer.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .semantics { contentDescription = nombreArtistaDescripcion }
                )
            }
        }

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = performer.image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build(),
            ),
            contentDescription = performer.name,
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .semantics { contentDescription = imagenArtistaDescripcion }
        )

        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 24.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        ) {
            val year = performer.birthDate.split("-")[0]
            Text(
                text = stringResource(R.string.anio_de_nacimiento) + year,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .semantics { contentDescription = fechaNacimientoArtista }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = performer.description,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .semantics { contentDescription = descripcionArtista }
            )
        }

    }


}