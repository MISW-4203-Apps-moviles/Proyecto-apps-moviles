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

class ArtistaDetailViewModel : ViewModel() {
    var vinylUiState: VinylUiState by mutableStateOf(VinylUiState.Loading)
        private set
    private val repository = PerformedRepository()

    private val _performer = MutableLiveData<Performer>()
    val performer: LiveData<Performer> = _performer

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    fun fetchPerformer(performerId: String) {
        viewModelScope.launch {
            vinylUiState = VinylUiState.Loading
            try {
                val performer = repository.getPerformer(performerId)
                vinylUiState = VinylUiState.Success
                _performer.value = performer
            } catch (e: IOException) {
                vinylUiState = VinylUiState.Error
            } catch (e: HttpException) {
                vinylUiState = VinylUiState.Error
            }


        }
    }
}

class ArtistaDetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistaDetailViewModel::class.java)) {
            return ArtistaDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}