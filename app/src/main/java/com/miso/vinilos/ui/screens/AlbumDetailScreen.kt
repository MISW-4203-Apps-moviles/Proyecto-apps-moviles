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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Comment
import com.miso.vinilos.ui.theme.onSurfaceVariantDark
import com.miso.vinilos.ui.theme.primaryContainerLight
import com.miso.vinilos.ui.theme.primaryDark
import com.miso.vinilos.ui.theme.primaryLightHighContrast
import com.miso.vinilos.ui.theme.scrimDark
import com.miso.vinilos.ui.theme.surfaceContainerHighLightHighContrast


@Composable
fun AlbumDetailScreen(
    album: Album?,
    isLoading: Boolean,
    fetchAlbum: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    LaunchedEffect(Unit) {
        fetchAlbum()
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
    ) {

        album?.let {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AlbumCard(
                    album = it,
                )

                CommentSection(
                    onAddComment = {}
                )
            }
        }
    }
}

@Composable
fun AlbumCard(
    album: Album,
) {
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


                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = album.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(text = album.name, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = album.cover)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build(),
                ),
                contentDescription = album.name,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )


            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 24.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            ) {
                val year = album.releaseDate.split("-")[0]
                Text(
                    text = year,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = album.recordLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = album.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
fun CommentItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Usuario",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Comentario",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun AddComment(
    onAddComment: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onAddComment,
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = "Agregar comentario") },
        containerColor = primaryDark,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp,
            )
    )
}

@Composable
fun CommentSection(
    comments: List<Comment> = emptyList(),
    onAddComment: () -> Unit
) {
    Column {
        Text(
            text = "Comentarios",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

    Column {
        repeat(2) {
            CommentItem()
            HorizontalDivider(color = primaryLightHighContrast)
        }
    }

    AddComment(onAddComment)
}

@Preview
@Composable
fun AddCommentPreview() {
    AddComment(
        onAddComment = {}
    )
}

@Preview
@Composable
fun CommentSectionPreview() {
    CommentSection(
        comments = emptyList(),
        onAddComment = {}
    )
}

@Preview
@Composable
fun CommentItemPreview() {
    CommentItem()
}

@Preview
@Composable
fun AlbumCardPreview() {
    AlbumCard(
        album = Album(
            id = 1,
            name = "Album name",
            description = "Album description",
            cover = "https://placehold.co/400x400.png",
            releaseDate = "2021-01-01",
            recordLabel = "Record label",
            genre = "Genre",
            performers = emptyList(),
            comments = emptyList()
        )
    )
}

@Preview
@Composable
fun AlbumDetailScreenPreview() {
    AlbumDetailScreen(
        album = Album(
            id = 1,
            name = "Album name",
            description = "Album description",
            cover = "https://placehold.co/400x400.png",
            releaseDate = "2021-01-01",
            recordLabel = "Record label",
            genre = "Genre",
            performers = emptyList(),
            comments = emptyList()
        ),
        isLoading = false,
        fetchAlbum = {}
    )
}