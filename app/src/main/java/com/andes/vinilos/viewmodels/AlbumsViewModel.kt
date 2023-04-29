package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.network.NetworkServiceAdapter

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<NewAlbum>>()
    /*private val albumsRepository = AlbumRepository(application)*/
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val albums: LiveData<List<NewAlbum>>
        get() = _albums
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFormNetwork()
    }

    private fun refreshDataFormNetwork() {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbums({
            val sortedAlbums = it.sortedBy { album -> album.name.lowercase() }
            _albums.postValue(sortedAlbums)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

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