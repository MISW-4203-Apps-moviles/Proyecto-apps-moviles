package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.miso.vinilos.viewModels.AlbumCreateViewModel

@Composable
fun AlbumCreateScreenHandler(
    navController: NavController,
    viewModel: AlbumCreateViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {

    val album by viewModel.album.observeAsState(null)

    AlbumCreateScreen(
        navController = navController,
        innerPadding = innerPadding,
        album = album
    )
}