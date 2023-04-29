package com.andes.vinilos.repositories

import android.app.Application
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError
import com.google.gson.Gson
import org.json.JSONObject

class AlbumRepository (val application: Application){

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun createAlbum(album: Album, onError: (VolleyError) -> Unit, onSuccess: (NewAlbum) -> Unit) {
        val requestBody = Gson().toJson(album)

        //Enviar la solicitud POST a través del adaptador de servicio de red
        networkServiceAdapter.createAlbum(
            JSONObject(requestBody),
            { response ->
                // El servidor responde con un objeto JSON que representa el nuevo álbum creado
                // Convertir el objeto JSON a un objeto Album
                val gson = Gson()
                val albumResponse = gson.fromJson(response.toString(), NewAlbum::class.java)
                // Devolver el nuevo álbum creado a través del callback onSuccess
                onSuccess(albumResponse)
            },
            onError
        )
    }
}