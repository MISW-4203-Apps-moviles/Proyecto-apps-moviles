package com.miso.vinilos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.models.Album
import com.miso.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.launch

class AlbumListViewModel : ViewModel() {
    private val repository = AlbumRepository()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    fun fetchAlbums() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val albums = repository.getAlbums()

                _albums.value = albums
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
                println(e)
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