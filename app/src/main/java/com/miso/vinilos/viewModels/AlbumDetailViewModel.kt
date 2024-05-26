package com.miso.vinilos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Collector
import com.miso.vinilos.models.NewCollector
import com.miso.vinilos.models.NewComment
import com.miso.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.launch

class AlbumDetailViewModel : ViewModel() {
    private val repository = AlbumRepository()

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isOpenDialog = MutableLiveData(false)
    val isOpenDialog: LiveData<Boolean> = _isOpenDialog

    fun toggleCommentModal() {
        _isOpenDialog.value = !_isOpenDialog.value!!
    }

    fun fetchAlbum(albumId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val album = repository.getAlbum(albumId)

                _album.value = album
                Log.d("DEBUG", "Album fetched: $album")
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
                println(e)
            }
        }
    }

    fun postComment(albumId: Int, comment: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _isOpenDialog.value = false
                Log.d("DEBUG", "Posting comment...")
                Log.d("DEBUG", "Album ID: $albumId")
                Log.d("DEBUG", "Comment: $comment")
                var description: NewComment = NewComment(
                    description = comment,
                    rating = 5,
                    collector = NewCollector(
                        id = 75
                    )
                )
                repository.postComment(albumId, description)
                fetchAlbum(albumId)
            } catch (e: Exception) {
                _isLoading.value = false
                println(e)
            }
        }
    }
}

class AlbumDetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}