package com.miso.vinilos.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.R
import com.miso.vinilos.models.Album
import com.miso.vinilos.ui.composables.VinylsTextField
import com.miso.vinilos.ui.composables.VynilsDatePicker
import com.miso.vinilos.ui.theme.VinylsTheme
import com.miso.vinilos.ui.theme.primaryDark
import com.miso.vinilos.ui.theme.secondaryDark
import com.miso.vinilos.viewModels.AlbumCreateViewModel
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlbumCreateScreen(
    navController: NavController = rememberNavController(),
    viewModel: AlbumCreateViewModel = AlbumCreateViewModel(),
    innerPadding: PaddingValues = PaddingValues()
) {

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        ) {
        CreateAlbumForm(navController = navController, viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAlbumForm(navController: NavController, viewModel: AlbumCreateViewModel) {
    val albumNameState = rememberSaveable { mutableStateOf("") }
    val albumCoverState = rememberSaveable { mutableStateOf("") }
    val albumDescriptionState = rememberSaveable { mutableStateOf("") }
    val albumReleaseDateState = rememberSaveable { mutableStateOf("") }
    val albumRecordLabelState = rememberSaveable { mutableStateOf("") }
    val albumGenreState = rememberSaveable { mutableStateOf("") }

    var showErrors by remember { mutableStateOf(false) }

    val isFormValid = albumNameState.value.isNotBlank() &&
            albumCoverState.value.isNotBlank() &&
            albumDescriptionState.value.isNotBlank() &&
            albumGenreState.value.isNotBlank() &&
            albumReleaseDateState.value.isNotBlank() &&
            albumRecordLabelState.value.isNotBlank()


    Spacer(modifier = Modifier.height(50.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        VinylsTextField(
            state = albumNameState,
            label = stringResource(R.string.nombre),
            placeholder = stringResource(R.string.digite_el_nombre_del_album),
            error = if (albumNameState.value.isBlank()) stringResource(R.string.nombre_requerido) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        VinylsTextField(
            state = albumCoverState,
            label = stringResource(R.string.portada),
            placeholder = stringResource(R.string.digite_la_url_de_la_portada),
            error = if (albumCoverState.value.isBlank()) stringResource(R.string.portada_requerida) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        VynilsDatePicker(
            state = albumReleaseDateState,
            label = stringResource(R.string.fecha_de_lanzamiento),
            error = if (albumReleaseDateState.value.isBlank()) stringResource(R.string.fecha_requerida) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        VinylsTextField(
            state = albumGenreState,
            label = stringResource(R.string.genero),
            placeholder = stringResource(R.string.digite_el_genero),
            error = if (albumGenreState.value.isBlank()) stringResource(R.string.genero_requerido) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        VinylsTextField(
            state = albumDescriptionState,
            label = stringResource(R.string.descripcion),
            placeholder = stringResource(R.string.digite_la_descripcion),
            singleLine = false,
            error = if (albumDescriptionState.value.isBlank()) stringResource(R.string.descripcion_requerida) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        VinylsTextField(
            state = albumRecordLabelState,
            label = stringResource(R.string.sello_discografico),
            placeholder = stringResource(R.string.digite_el_sello_discografico),
            error = if (albumRecordLabelState.value.isBlank()) stringResource(R.string.sello_discografico_requerido) else null,
            forceShowError = showErrors
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.popBackStack()
                },
                icon = { Icon(Icons.Outlined.Cancel, stringResource(R.string.cancelar)) },
                text = { Text(text = stringResource(R.string.cancelar)) },
                containerColor = secondaryDark,
                modifier = Modifier
                    .padding(start = 0.dp, end = 12.dp)
                    .weight(1f)
                    .fillMaxWidth()
            )
            ExtendedFloatingActionButton(
                onClick = {
                    if (isFormValid) {

                        val releaseDateInput = LocalDate.parse(
                            albumReleaseDateState.value, DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        )
                        val releaseDateOutput = releaseDateInput
                            .atStartOfDay()
                            .atOffset(ZoneOffset.ofHours(-5))
                            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                        viewModel.createAlbum(
                            Album(
                                name = albumNameState.value,
                                cover = albumCoverState.value,
                                releaseDate = releaseDateOutput,
                                description = albumDescriptionState.value,
                                recordLabel = albumRecordLabelState.value,
                                genre = albumGenreState.value,
                            )
                        )
                        navController.popBackStack()
                    } else {
                        showErrors = true
                    }
                },
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.agregar_album)) },
                text = { Text(text = stringResource(R.string.agregar_album)) },
                containerColor = primaryDark,
                modifier = Modifier
                    .padding(start = 12.dp, end = 0.dp)
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AlbumCreatePreview() {
    VinylsTheme(darkTheme = true) {
        AlbumCreateScreen()
    }
}