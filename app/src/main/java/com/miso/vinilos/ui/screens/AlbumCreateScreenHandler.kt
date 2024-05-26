package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.miso.vinilos.viewModels.AlbumCreateViewModel

@Composable
fun AlbumCreateScreenHandler(
    viewModel: AlbumCreateViewModel,
    navController: NavController,
    innerPadding: PaddingValues = PaddingValues()
) {

    AlbumCreateScreen(
        navController = navController,
        viewModel = viewModel,
        innerPadding = innerPadding,
    )
}