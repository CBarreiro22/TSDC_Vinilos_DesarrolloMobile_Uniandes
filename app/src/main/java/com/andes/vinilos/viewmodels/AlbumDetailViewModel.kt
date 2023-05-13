package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.models.Album

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application){

    private val _albumMutableData = MutableLiveData<Album>()

    // LiveData to expose the list of albums to the view
    val album: LiveData<Album>
        get() = _albumMutableData

    // Refresh album data from the network
    fun refreshData(currentAlbum: Album) {
        _albumMutableData.postValue(currentAlbum)
    }

    init {
    }

    // Factory class to create an instance of AlbumsViewModel
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}