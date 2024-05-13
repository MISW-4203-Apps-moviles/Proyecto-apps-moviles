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
import com.miso.vinilos.models.Performer
import com.miso.vinilos.repositories.PerformedRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ArtistasListViewModel : ViewModel() {

    /** Status of the view */
    var vinylUiState: VinylUiState by mutableStateOf(VinylUiState.Loading)

    private val repository = PerformedRepository()

    private val _performers = MutableLiveData<List<Performer>>()
    val performers: LiveData<List<Performer>> = _performers

    fun fetchPerformers() {
        viewModelScope.launch {
            vinylUiState = VinylUiState.Loading
            try {
                val performers = repository.getPerformers()
                Log.d("AListViewModelFactory", "fetchPerformers: $performers")
                vinylUiState = VinylUiState.Success
                _performers.value = performers
            } catch (e: IOException) {
                vinylUiState = VinylUiState.Error
            } catch (e: HttpException) {
                vinylUiState = VinylUiState.Error
            }
        }
    }
}

class ArtistasListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistasListViewModel::class.java)) {
            return ArtistasListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}