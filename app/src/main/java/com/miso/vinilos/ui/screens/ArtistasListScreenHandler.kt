package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.ui.composables.ErrorScreen
import com.miso.vinilos.ui.composables.SpinnerScreen
import com.miso.vinilos.viewModels.ArtistasListViewModel

@Composable
fun ArtistaListScreenHandler(
    vinylUiState: VinylUiState,
    retryAction: () -> Unit,
    viewModel: ArtistasListViewModel,
    navigateToPerformerDetail: (performerId: String) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val performers by viewModel.performers.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPerformer()
    }

    when (vinylUiState) {
        is VinylUiState.Loading -> SpinnerScreen()
        is VinylUiState.Success -> ArtistasListScreen(
            navigateToPerformerDetail = navigateToPerformerDetail,
            innerPadding = innerPadding,
            performers = performers,
        )
        is VinylUiState.Error -> ErrorScreen(retryAction)
    }
}