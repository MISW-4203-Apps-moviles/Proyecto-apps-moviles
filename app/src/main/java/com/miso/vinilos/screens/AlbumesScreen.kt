package com.miso.vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.miso.vinilos.ui.theme.VinilosTheme

@Composable
fun AlbumesScreen(innerPadding: PaddingValues = PaddingValues()) {

    Column(modifier = Modifier.padding(innerPadding)) {
        ListItem(
            overlineContent = { Text("Love") },
            headlineContent = { Text("Forever Changes (1967)") },
            leadingContent = {
                Image(
                    painter = rememberImagePainter(
                        data = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "Portada de Forever Changes",
                    modifier = Modifier.size(48.dp),
                )
            }
        )
        ListItem(
            overlineContent = { Text("Ramones") },
            headlineContent = { Text("Ramones (1976)") },
            leadingContent = {
                Image(
                    painter = rememberImagePainter(
                        data = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "Portada de Ramones",
                    modifier = Modifier.size(48.dp),
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumesScreenPreview() {
    VinilosTheme {
        AlbumesScreen()
    }
}