package com.miso.vinilos.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miso.vinilos.screens.viewModels.AlbumListViewModel
import com.miso.vinilos.ui.theme.VinilosTheme
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AlbumesScreen(
    viewModel: AlbumListViewModel = viewModel(),
    innerPadding: PaddingValues = PaddingValues(),
    navigateToAlbumDetail: (albumId: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.fetchAlbums();

    println(viewModel.getAlbums())
    Text(
        text = "Pantalla de álbumes", modifier = Modifier.padding(innerPadding)
    )

    Button(onClick = { navigateToAlbumDetail("1") }) {
        Text("Ir a detalle de álbum")
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