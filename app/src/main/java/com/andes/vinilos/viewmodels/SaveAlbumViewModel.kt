package com.andes.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andes.vinilos.models.Album
import com.andes.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject
import com.android.volley.VolleyError

class SaveAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun saveAlbum(Album: Album, onSuccess: () -> Unit, onError: () -> Unit) {
        val jsonObject = JSONObject()
        jsonObject.put("name", Album.name)
        jsonObject.put("cover", Album.cover)
        jsonObject.put("recordLabel", Album.recordLabel)
        jsonObject.put("releaseDate", Album.releaseDate)
        jsonObject.put("genre", Album.genre)
        jsonObject.put("description", Album.description)

        networkServiceAdapter.createAlbum(
            jsonObject,
            onComplete = { response ->
                Log.d("SaveAlbumViewModel", "Album created successfully")
                onSuccess()
            },
            onError = { error: VolleyError ->
                Log.d("SaveAlbumViewModel", "Error creating album: $error")
                onError()
            }
        )
    }
}

