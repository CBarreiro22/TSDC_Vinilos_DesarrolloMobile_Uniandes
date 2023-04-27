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

class AlbumsViewModel (application: Application) :  AndroidViewModel(application){
    private val albumsRepository = AlbumRepository(application)

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    init {
        val album = Album(
            "nombre creado desde android",
            "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            "1984-08-01T00:00:00-05:00",
            "esto es super chevere",
            "Elektra")
        Log.d("AlbumsViewModel", "Album: $album")
        testCreateAlbum(album)
    }
    fun testCreateAlbum(album: Album) {
        albumsRepository.createAlbum(
            album,
            onSuccess = {
                Log.d("AlbumsViewModel", "Album created successfully")
            },
            onError = { error ->
                Log.d("AlbumsViewModel", "Error creating album: $error")
            }
        )
    }


}