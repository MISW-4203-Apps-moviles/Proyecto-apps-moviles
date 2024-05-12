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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.miso.vinilos.data.VinylUiState
import com.miso.vinilos.models.Collector
import com.miso.vinilos.repositories.AlbumRepository
import com.miso.vinilos.repositories.CollectionRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ColeccionistasListViewModel() : ViewModel() {

    /** Status of the view */
    var vinylUiState: VinylUiState by mutableStateOf(VinylUiState.Loading)
        private set

    private val repository = CollectionRepository()

    private val _collections = MutableLiveData<List<Collector>>()
    val collections: LiveData<List<Collector>> = _collections


    fun fetchCollections() {
        viewModelScope.launch {
            vinylUiState = VinylUiState.Loading
            try {
                val collections = repository.getCollections()
                Log.d("CListViewModel", "fetchCollections: $collections")
                vinylUiState = VinylUiState.Success
                _collections.value = collections
            } catch (e: IOException) {
                vinylUiState = VinylUiState.Error
            } catch (e: HttpException) {
                vinylUiState = VinylUiState.Error
            }
        }
    }
}

class ColeccionistasListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColeccionistasListViewModel::class.java)) {
            return ColeccionistasListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}