package com.miso.vinilos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miso.vinilos.ui.theme.VinilosTheme

@Composable
fun ArtistasScreen(innerPadding: PaddingValues = PaddingValues()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pantalla de artistas"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistasScreenPreview() {
    VinilosTheme {
        ArtistasScreen()
    }
}