package com.miso.vinilos.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.miso.vinilos.viewModels.AlbumCreateViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlbumCreateScreenHandler(
    navController: NavController,
    viewModel: AlbumCreateViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {

    AlbumCreateScreen(
        navController = navController,
        innerPadding = innerPadding,
    )
}