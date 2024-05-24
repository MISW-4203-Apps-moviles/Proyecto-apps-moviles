package com.miso.vinilos.viewModels

import com.miso.vinilos.models.Collector
import com.miso.vinilos.repositories.CollectionRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.data.VinylUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ColeccionistaDetailViewModel : ViewModel() {
    var vinylUiState: VinylUiState by mutableStateOf(VinylUiState.Loading)

    private val repository = CollectionRepository()

    private val _collector = MutableLiveData<Collector>()
    val collector: LiveData<Collector> = _collector


    fun fetchCollector(collectorId: String) {
        viewModelScope.launch {
            vinylUiState = VinylUiState.Loading
            try {
                val collector = repository.getCollector(collectorId)
                vinylUiState = VinylUiState.Success
                _collector.value = collector
            } catch (e: IOException) {
                vinylUiState = VinylUiState.Error
            } catch (e: HttpException) {
                vinylUiState = VinylUiState.Error
            }


        }
    }
}

class ColeccionistaDetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColeccionistaDetailViewModel::class.java)) {
            return ColeccionistaDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}