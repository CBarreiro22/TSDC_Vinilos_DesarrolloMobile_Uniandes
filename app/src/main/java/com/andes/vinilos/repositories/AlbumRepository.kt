package com.andes.vinilos.repositories

import android.app.Application
import com.andes.vinilos.models.Album
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError


class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbums({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }
}