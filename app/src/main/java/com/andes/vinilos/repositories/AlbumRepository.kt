package com.andes.vinilos.repositories

import android.app.Application
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){

    suspend fun refreshData(): List<NewAlbum> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}