package com.miso.vinilos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.models.Album
import com.miso.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.launch


class AlbumCreateViewModel : ViewModel() {
    private val repository = AlbumRepository()

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    fun createAlbum(album: Album) {
        viewModelScope.launch {
            repository.createAlbum(album)
        }
    }

class AlbumCreateViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
            return AlbumCreateViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}