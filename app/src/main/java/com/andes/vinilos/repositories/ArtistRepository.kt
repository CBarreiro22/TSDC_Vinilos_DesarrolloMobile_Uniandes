package com.andes.vinilos.repositories

import android.app.Application
import com.andes.vinilos.models.Musician
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class ArtistRepository (val application: Application) {

    suspend fun refreshData(): List<Musician> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getMusician()
    }

}