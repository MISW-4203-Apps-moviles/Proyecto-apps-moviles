package com.miso.vinilos.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.models.Album
import com.miso.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AlbumListViewModel : ViewModel() {

    /** Status of the view */
    var vinylUiState: VinylUiState by mutableStateOf(VinylUiState.Loading)

    private val repository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    /**
     * Call fetchAlbums() on init so we can display status immediately.
     */
    //init {
    //    if (vinylUiState == VinylUiState.Loading) {
    //        fetchAlbums()
    //    }
    //}

    fun fetchAlbums() {
        viewModelScope.launch {
            vinylUiState = VinylUiState.Loading
            try {
                val albums = repository.getAlbums()
                Log.d("AlbumListViewModel", "fetchAlbums: $albums")
                vinylUiState = VinylUiState.Success
                _albums.value = albums
            } catch (e: IOException) {
                vinylUiState = VinylUiState.Error
            } catch (e: HttpException) {
                vinylUiState = VinylUiState.Error
            }
        }
    }
}

class AlbumListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
            return AlbumListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}