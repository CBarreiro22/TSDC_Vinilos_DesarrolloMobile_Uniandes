package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.models.Musician
import com.andes.vinilos.repositories.ArtistsRepository

class ArtistsViewModel(application: Application) : AndroidViewModel(application) {
    // MutableLiveData to hold the list of albums retrieved from the repository
    private val _artists = MutableLiveData<List<Musician>>(listOf())

    // Comparator to sort the list of albums by name in ascending order
    private val artistNameComparator = compareBy<Musician> { it.name.lowercase() }

    // Repository to retrieve album data from the network
    private val artistRepository = ArtistsRepository(application)

    // MutableLiveData to track network errors
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    // MutableLiveData to track whether the network error has been shown to the user
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    // LiveData to expose the list of albums to the view
    val artists: LiveData<List<Musician>>
        get() = _artists

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
        artistRepository.refreshData({
            // Sort the list of albums by name in ascending order and update the MutableLiveData
            _artists.postValue(it.sortedWith(artistNameComparator))

            // Set the network error and isNetworkErrorShown LiveData to false
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            // Set the network error LiveData to true if an error occurred while refreshing data
            _eventNetworkError.value = true
        })
    }
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    // Factory class to create an instance of AlbumsViewModel
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}