package com.andes.vinilos.network

import android.content.Context
import android.util.Log
import com.andes.vinilos.models.Musician
import com.andes.vinilos.models.NewAlbum
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://appvinilos2023.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkServiceAdapter(context).also {
                instance = it
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    fun createAlbum(
        body: JSONObject,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        Log.d("create album", "body$body")
        requestQueue.add(
            postRequest(
                "albums", body,
                { response ->
                    onComplete(response)
                },
                {
                    onError(it)
                }, null
            )
        )

    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
        headers: HashMap<String, String>? = null
    ): JsonObjectRequest {
        return object : JsonObjectRequest(
            Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        ) {
            override fun getHeaders(): Map<String, String> {
                Log.d("networkservice adapter", "adding headers")
                val headersMap = HashMap<String, String>()
                headersMap["Content-Type"] = "application/json"
                headersMap["Accept"] = "*/*"
                headersMap["Accept-Encoding"] = "gzip, deflate, br"
                headers?.let { headersMap.putAll(it) }
                return headersMap
            }

            override fun getRetryPolicy(): RetryPolicy {
                return DefaultRetryPolicy(
                    5000,  // 5 seconds timeout
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            }

            fun onErrorResponse(error: VolleyError?) {

                Log.e(
                    "NetworkServiceAdapter",
                    "Error al hacer la petición: ${error?.networkResponse?.statusCode} ${
                        error?.networkResponse?.data?.toString(Charsets.UTF_8)
                    }"
                )
            }
        }
    }


    fun getAlbums(
        onComplete: (resp: List<NewAlbum>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(getRequest("albums", { response ->
            val resp = JSONArray(response)
            val list = mutableListOf<NewAlbum>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                list.add(
                    i, NewAlbum(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description")
                    )
                )
            }
            onComplete(list)
        }, {
            onError(it)
        }))
    }

    fun getMusicians(
        onComplete: (resp: List<Musician>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(getRequest("musicians", { response ->
            val resp = JSONArray(response)
            val list = mutableListOf<Musician>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                val albumList = mutableListOf<NewAlbum>()
                val albumsArray = item.getJSONArray("albums")
                // Itera el arreglo de albums utilizando otro bucle for
                for (j in 0 until albumsArray.length()) {
                    val albumObject = albumsArray.getJSONObject(j)
                    val albumId = albumObject.getInt("id")
                    val albumName = albumObject.getString("name")
                    val albumCover = albumObject.getString("cover")
                    val releaseDate = albumObject.getString("releaseDate")
                    val description = albumObject.getString("description")
                    val genre = albumObject.getString("genre")
                    val recordLabel = albumObject.getString("recordLabel")
                    albumList.add(
                        j,
                        NewAlbum(
                            name = albumName,
                            cover = albumCover,
                            releaseDate = releaseDate,
                            description = description,
                            genre = genre,
                            recordLabel = recordLabel,
                            id = albumId
                        )
                    )


                    // Accede a las propiedades del álbum
                }
                list.add(
                    i, Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"), albums = albumList
                    )
                )
            }
            onComplete(list)
        }, {
            onError(it)

        }))
    }
}
