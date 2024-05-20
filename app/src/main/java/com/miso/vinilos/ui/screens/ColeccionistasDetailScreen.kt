package com.miso.vinilos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.R
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Collector
import com.miso.vinilos.models.Comment
import com.miso.vinilos.models.Performer
import com.miso.vinilos.ui.composables.ListDivider

@Composable
fun ColeccionistasDetailScreen(
    collector: Collector?,
    innerPadding: PaddingValues = PaddingValues(),
    navigateToCollectorDetail: (collectorId: Int) -> Unit,

    ) {
    val comentariosDesc: String = stringResource(R.string.desc_collect_comentarios)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ){
        collector?.let {
            item {
                CollectorCard(collector = it)
            }
            item {
                Text(
                    text = comentariosDesc,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            if (it.comments.isNotEmpty()) {
                item {
                    CommentListSection(comments = it.comments)
                }

            } else {
                item {
                    Text(
                        text = stringResource(R.string.desc_no_comentarios_disponibles),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CollectorCard(
    collector: Collector,
) {
    val nombreCollector = stringResource(R.string.desc_nombre_del_coleccionista)
    val emailCollector = stringResource(R.string.desc_email_del_coleccionista)
    val phoneCollector = stringResource(R.string.desc_telefono_del_coleccionista)

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
                    text = collector.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .semantics { contentDescription = nombreCollector }
                )
            }
        }


        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 24.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        ) {

            Text(
                text = collector.email,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .semantics { contentDescription = emailCollector }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = collector.telephone,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .semantics { contentDescription = phoneCollector }
            )
        }
    }
}

@Composable
fun CommentListSection(
    comments: List<Comment>,
    innerPadding: PaddingValues = PaddingValues()
) {
    val listDescription = stringResource(R.string.lista_de_comentarios)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics { contentDescription = listDescription }
        ) {
            items(comments, key = { comment -> comment.id }) { comment ->
                CommentItem(
                    comment = comment
                )
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment
) {

    val nombreCommentDescripcion = stringResource(R.string.descripcion_del_comment)

    ListDivider()
    ListItem(

        overlineContent = {

        },
        headlineContent = {
            Text(
                text = comment.description,
                modifier = Modifier.semantics { contentDescription = nombreCommentDescripcion }
            )
        },
    )
}