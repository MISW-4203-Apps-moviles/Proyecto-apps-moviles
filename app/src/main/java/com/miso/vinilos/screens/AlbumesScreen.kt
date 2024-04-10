package com.miso.vinilos.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miso.vinilos.ui.theme.VinilosTheme

@Composable
fun AlbumesScreen(innerPadding: PaddingValues = PaddingValues()) {
    Text(
        text = "Pantalla de álbumes", modifier = Modifier.padding(innerPadding)
    )
}

@Preview(showBackground = true)
@Composable
fun AlbumesScreenPreview() {
    VinilosTheme {
        AlbumesScreen()
    }
}