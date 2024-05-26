package com.miso.vinilos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.miso.vinilos.R
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Comment
import com.miso.vinilos.ui.composables.ListDivider
import com.miso.vinilos.ui.theme.VinylsTheme
import com.miso.vinilos.ui.theme.primaryDark


@Composable
fun AlbumDetailScreen(
    album: Album?,
    isLoading: Boolean,
    fetchAlbum: () -> Unit,
    toggleCommentModal: () -> Unit,
    onCommentAdded: (String) -> Unit,
    isOpenDialog: Boolean = false,
    comments: List<Comment>,
    innerPadding: PaddingValues = PaddingValues()
) {
    val loadingDescription = stringResource(R.string.cargando_album_descripcion)

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
            CircularProgressIndicator(
                modifier = Modifier.semantics { contentDescription = loadingDescription }
            )
        }
    }

    if (isOpenDialog) {
        CommentDialog(
            onDialogDismiss = toggleCommentModal,
            onCommentAdded = onCommentAdded
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
    ) {

        album?.let {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                AlbumCard(
                    album = it,
                )

                CommentSection(
                    onAddComment = toggleCommentModal,
                    comments = comments
                )
            }
        }
    }
}

@Composable
fun CommentDialog(
    onDialogDismiss: () -> Unit,
    onCommentAdded: (String) -> Unit
    ) {
    var commentText by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var maxChar = 50;

    AlertDialog(
        onDismissRequest = { onDialogDismiss() },
        title = { Text(text = "Agregar comentario", fontSize = MaterialTheme.typography.titleMedium.fontSize) },
        text = {
            Column {
                TextField(
                    value = commentText,
                    onValueChange = {
                        if (it.length <= maxChar) commentText = it
                        errorMessage =
                            if (it.length in 5..50) "" else "El comentario debe tener entre 5 y 50 caracteres"
                    },
                    singleLine = true,
                    isError = errorMessage.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Limpiar texto",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Digite el comentario",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    },
                    label = { Text(text = "comentario") },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedLabelColor = MaterialTheme.colorScheme.scrim,
                        focusedLabelColor = MaterialTheme.colorScheme.scrim,
                        errorLabelColor = MaterialTheme.colorScheme.error,
                        errorContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorIndicatorColor = MaterialTheme.colorScheme.error,
                        errorPlaceholderColor = MaterialTheme.colorScheme.error,
                        // EL FONDO DEBE TENER UNA TONALIDAD TRANSPARENT PARA QUE SE PUEDA VER EL FONDO DE LA PANTALLA
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        dismissButton = {
            Button(onClick = onDialogDismiss,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    containerColor = MaterialTheme.colorScheme.inverseSurface
                )
                ) {
                Text("Cancelar")
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                   contentColor = MaterialTheme.colorScheme.onSecondary,
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                enabled = commentText.length in 5..50,
                onClick = {
                if (commentText.length in 5..50) {
                    onCommentAdded(commentText)
                }

            }) {
                Text("Agregar")
            }
        },
    )
}

@Composable
fun AlbumCard(
    album: Album,
) {
    val albumNombreDescripcion = stringResource(R.string.album_nombre_descripcion)
    val albumArtistaNombreDescripcion = stringResource(R.string.album_artista_nombre_descripcion)
    val albumAnioDescripcion = stringResource(R.string.album_anio_descripcion)
    val albumDescripcion = stringResource(R.string.album_descripcion)
    val disqueraDescripcion = stringResource(R.string.album_disquera_descripcion)
    val albumPortadaDescripcion = stringResource(R.string.album_portada_descripcion)

    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape =  RoundedCornerShape(12.dp),
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
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .semantics { contentDescription = albumNombreDescripcion }
                    )

                    Text(
                        text = album.performers?.getOrNull(0)?.name ?: stringResource(R.string.sin_artista),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .semantics { contentDescription = albumArtistaNombreDescripcion }
                    )
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
                    .semantics { contentDescription = albumPortadaDescripcion }
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
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .semantics { contentDescription = albumAnioDescripcion }
                )

                Text(
                    text = album.recordLabel,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .semantics { contentDescription = disqueraDescripcion }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = album.description,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .semantics { contentDescription = albumDescripcion }
                )
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: String,
    user: String
) {
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
                    text = user,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddComment(
    onAddComment: () -> Unit
) {
    Row {
        ExtendedFloatingActionButton(
            onClick = onAddComment,
            icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
            text = { Text(text = stringResource(R.string.agregar_comentario)) },
            containerColor = primaryDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .border(
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
                )
                .height(36.dp)
        )
    }

}

@Composable
fun CommentSection(
    comments: List<Comment>,
    onAddComment: () -> Unit
) {
    Column {
        Text(
            text = "Comentarios",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

    Column {
        comments.forEach { comment ->
            CommentItem(
                comment = comment.description,
                user = "Usuario"
            )
            ListDivider()
        }
    }

    AddComment(onAddComment)
}

@Preview
@Composable
fun AddCommentPreview() {
    VinylsTheme(darkTheme = true) {
        AddComment(
            onAddComment = {}
        )
    }
}

@Preview
@Composable
fun CommentSectionPreview() {
    VinylsTheme(darkTheme = true) {
        CommentSection(
            comments = emptyList(),
            onAddComment = {}
        )
    }
}

@Preview
@Composable
fun CommentItemPreview() {
   VinylsTheme(darkTheme = true) {
      CommentItem(
          comment = "This is a comment",
          user = "User"
      )
   }
}

@Preview
@Composable
fun AlbumCardPreview() {
    VinylsTheme(darkTheme = true) {
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
}

@Preview
@Composable
fun AlbumDetailScreenPreview() {
  VinylsTheme(darkTheme = true) {
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
          fetchAlbum = {},
          isOpenDialog = false,
          toggleCommentModal = {},
          onCommentAdded = {},
          comments = emptyList()
      )
    }
}