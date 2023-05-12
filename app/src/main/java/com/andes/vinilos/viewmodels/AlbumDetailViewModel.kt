package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.models.NewAlbum

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application){

    private val _albumMutableData = MutableLiveData<NewAlbum>()

    // LiveData to expose the list of albums to the view
    val album: LiveData<NewAlbum>
        get() = _albumMutableData

    // Refresh album data from the network
    fun refreshData(currentAlbum: NewAlbum) {
        val f = currentAlbum
//        _albumMutableData.postValue(currentAlbum)
    }

//     Initialize the ViewModel by refreshing data from the network
    init {
    }

    // Factory class to create an instance of AlbumsViewModel
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}