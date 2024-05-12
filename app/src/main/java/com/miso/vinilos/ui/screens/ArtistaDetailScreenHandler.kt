package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.ui.composables.ErrorScreen
import com.miso.vinilos.ui.composables.SpinnerScreen
import com.miso.vinilos.viewModels.ArtistaDetailViewModel

@Composable
fun ArtistaDetailScreenHandler(
    vinylUiState: VinylUiState,
    artistaId: String,
    retryAction: () -> Unit,
    viewModel: ArtistaDetailViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {
    val performer by viewModel.performer.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPerformer(artistaId)
    }

    when (vinylUiState) {
        is VinylUiState.Loading -> SpinnerScreen()
        is VinylUiState.Success -> ArtistaDetailScreen(
            innerPadding = innerPadding,
            performer = performer,
        )
        is VinylUiState.Error -> ErrorScreen(retryAction)
    }
}