package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AlbumCreateScreenHandler(
    navController: NavController,
    innerPadding: PaddingValues = PaddingValues()
) {

    AlbumCreateScreen(
        navController = navController,
        innerPadding = innerPadding,
    )
}