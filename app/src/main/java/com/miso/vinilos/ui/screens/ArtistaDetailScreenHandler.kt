package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.viewModels.ArtistaDetailViewModel

@Composable
fun ArtistaDetailScreenHandler(
    viewModel: ArtistaDetailViewModel,
    artistaId: String,
    innerPadding: PaddingValues = PaddingValues()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val performer by viewModel.performer.observeAsState(null)

    ArtistaDetailScreen(
        innerPadding = innerPadding,
        isLoading = isLoading,
        performer = performer,
        fetchPerformer = { viewModel.fetchPerformer(artistaId) }
    )
}