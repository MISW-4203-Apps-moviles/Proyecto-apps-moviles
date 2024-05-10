package com.miso.vinilos.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.ui.composables.ErrorScreen
import com.miso.vinilos.ui.composables.SpinnerScreen
import com.miso.vinilos.viewModels.AlbumListViewModel
import com.miso.vinilos.viewModels.ColeccionistasListViewModel

@Composable
fun ColeccionistasListScreenHandler(
    vinylUiState: VinylUiState,
    retryAction: () -> Unit,
    viewModel: ColeccionistasListViewModel,
    navigateToCollectionDetail: (collectionId: Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val collections by viewModel.collections.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchCollections()
    }

    when (vinylUiState) {
        is VinylUiState.Loading -> SpinnerScreen()
        is VinylUiState.Success -> ColeccionistasListScreen(
            navigateToCollectionDetail = navigateToCollectionDetail,
            innerPadding = innerPadding,
            collections = collections,
        )
        is VinylUiState.Error -> ErrorScreen(retryAction)
    }
}