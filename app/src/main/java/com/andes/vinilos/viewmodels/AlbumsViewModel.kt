package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.network.NetworkServiceAdapter
import com.andes.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {
    // MutableLiveData to hold the list of albums retrieved from the repository
    private val _albums = MutableLiveData<List<NewAlbum>>(listOf())

    // Comparator to sort the list of albums by name in ascending order
    private val albumNameComparator = compareBy<NewAlbum> { it.name.lowercase() }

    // Repository to retrieve album data from the network
    private val albumsRepository = AlbumRepository(application)

    // MutableLiveData to track network errors
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    // MutableLiveData to track whether the network error has been shown to the user
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    // LiveData to expose the list of albums to the view
    val albums: LiveData<List<NewAlbum>>
        get() = _albums

    // LiveData to expose the network error status to the view
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    // LiveData to expose the network error shown status to the view
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    // Initialize the ViewModel by refreshing data from the network
    init {
        refreshDataFromNetwork()
    }

    // Refresh album data from the network
    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshData()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    // Call this function to indicate that the network error has been shown to the user
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    // Factory class to create an instance of AlbumsViewModel
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}