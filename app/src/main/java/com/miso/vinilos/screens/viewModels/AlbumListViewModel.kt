package com.miso.vinilos.screens.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AlbumListUiState(
    val albums: List<AlbumViewModel>
)

data class AlbumViewModel(
    val id: String,
    val title: String,
    val artist: String,
    val year: String,
    val imageUrl: String
)

class AlbumListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumListUiState(albums = emptyList()))
    val uiState: StateFlow<AlbumListUiState> = _uiState.asStateFlow()

    fun fetchAlbums() {
        _uiState.update {
            AlbumListUiState(
                albums = listOf(
                    AlbumViewModel(
                        id = "1",
                        title = "The Dark Side of the Moon",
                        artist = "Pink Floyd",
                        year = "1973",
                        imageUrl = "https://img.discogs.com/"
                    ),
                    AlbumViewModel(
                        id = "2",
                        title = "Abbey Road",
                        artist = "The Beatles",
                        year = "1969",
                        imageUrl = "https://img.discogs.com/"
                    ),
                ),
            )
        }
    }

    fun getAlbums(): List<AlbumViewModel> {
        return _uiState.value.albums
    }
}