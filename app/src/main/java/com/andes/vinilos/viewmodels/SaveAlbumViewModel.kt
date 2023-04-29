package com.andes.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.models.Album
import com.andes.vinilos.repositories.AlbumRepository

class SaveAlbumViewModel(application: Application) :  AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private val albumsRepository = AlbumRepository(application)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    fun saveAlbum(album: Album, onSuccess: () -> Unit, onError: () -> Unit) {
        albumsRepository.createAlbum(
            album,
            onSuccess = {
                Log.d("AlbumsViewModel", "Album created successfully")
                onSuccess()
            },
            onError = { error ->
                Log.d("AlbumsViewModel", "Error creating album: $error")
                onError()
            }
        )
    }




}