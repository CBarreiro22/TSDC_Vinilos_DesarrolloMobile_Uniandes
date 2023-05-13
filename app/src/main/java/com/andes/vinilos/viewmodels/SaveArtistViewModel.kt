package com.andes.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andes.vinilos.models.Musician
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError
import org.json.JSONObject

class SaveArtistViewModel(application: Application) : AndroidViewModel(application) {
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
    fun saveArtist(artist: Musician, onSuccess: () -> Unit, onError: () -> Unit) {
        val jsonObject = JSONObject()
        jsonObject.put("name", artist.name)
        jsonObject.put("image", artist.image)
        jsonObject.put("description", artist.description)
        jsonObject.put("birthDate", artist.birthDate)
        networkServiceAdapter.createMusician(jsonObject, { response ->
            Log.d("SaveArtistViewModel", "Artist created successfully")
            onSuccess()
        }, { error: VolleyError ->
            Log.d("SaveArtistViewModel", "Error creating artist: $error")
            onError()
        })
    }
}