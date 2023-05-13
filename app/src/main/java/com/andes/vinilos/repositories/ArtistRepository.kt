package com.andes.vinilos.repositories

import android.app.Application
import android.util.Log
import com.andes.vinilos.models.Musician
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class ArtistsRepository  (val application: Application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)
    fun refreshData(callback: (List<Musician>)->Unit, onError: (VolleyError)->Unit) {
        Log.i("artitsts repository", "refreshData")
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        networkServiceAdapter.getMusicians({
            //Guardar los artistas de la variable it en un almacén de datos local para uso futuro
            Log.i("networkServiceAdapter","geting musicians")
            callback(it)
        },
            onError
        )
    }
}