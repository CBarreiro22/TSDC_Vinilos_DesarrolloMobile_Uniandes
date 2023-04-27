package com.andes.vinilos.repositories

import android.app.Application
import com.andes.vinilos.models.Album
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError
import com.google.gson.Gson
import org.json.JSONObject

class AlbumRepository (val application: Application){

    fun createAlbum(album: Album, onError: (VolleyError) -> Unit, onSuccess: (Album) -> Unit) {
        //Enviar la solicitud POST a través del adaptador de servicio de red
        val requestBody = JSONObject(Gson().toJson(album))

        NetworkServiceAdapter.getInstance(application).createAlbum(requestBody,
            { newAlbum:Album ->
                //Devolver el nuevo álbum creado a través del callback
                onSuccess(newAlbum)
            },
            onError
        )
    }
}