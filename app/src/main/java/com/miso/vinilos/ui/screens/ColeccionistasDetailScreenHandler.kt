package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.ui.composables.ErrorScreen
import com.miso.vinilos.ui.composables.SpinnerScreen
import com.miso.vinilos.viewModels.ColeccionistaDetailViewModel

@Composable
fun ColeccionistasDetailScreenHandler(
    vinylUiState: VinylUiState,
    coleccionistaId: String,
    retryAction: () -> Unit,
    viewModel: ColeccionistaDetailViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {
    val collector by viewModel.collector.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCollector(coleccionistaId)
    }

    when (vinylUiState) {
        is VinylUiState.Loading -> SpinnerScreen()
        is VinylUiState.Success -> ColeccionistasDetailScreen(
            innerPadding = innerPadding,
            collector = collector
        )
        is VinylUiState.Error -> ErrorScreen(retryAction)
    }
}