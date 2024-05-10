package com.miso.vinilos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miso.vinilos.models.Performer
import com.miso.vinilos.repositories.PerformedRepository
import kotlinx.coroutines.launch

class ArtistaDetailViewModel : ViewModel() {
    private val repository = PerformedRepository()

    private val _performer = MutableLiveData<Performer>()
    val performer: LiveData<Performer> = _performer

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    fun fetchPerformer(performerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val performer = repository.getPerformer(performerId)
                _performer.value = performer
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                println(e)
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