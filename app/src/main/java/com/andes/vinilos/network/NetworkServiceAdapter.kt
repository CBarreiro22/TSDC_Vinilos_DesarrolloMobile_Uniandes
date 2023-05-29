package com.andes.vinilos.network

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.Musician
import com.andes.vinilos.models.Prize
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


private class FillResponseAlbumTask : AsyncTask<String, Void, MutableList<Album>>() {
    override fun doInBackground(vararg response: String): MutableList<Album> {
        val resp = JSONArray(response[0])
        val list = mutableListOf<Album>()
        for (i in 0 until resp.length()) {
            val item = resp.getJSONObject(i)
            list.add(
                i, Album(
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
        return list
    }
}

private class FillResponseArtistTask : AsyncTask<String, Void, MutableList<Musician>>() {
    override fun doInBackground(vararg response: String): MutableList<Musician> {
        val list = mutableListOf<Musician>()
        val resp = JSONArray(response[0])
        for (i in 0 until resp.length()) {
            val item = resp.getJSONObject(i)
            val albumList = mutableListOf<Album>()
            val albumsArray = item.getJSONArray("albums")
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
                    Album(
                        name = albumName,
                        cover = albumCover,
                        releaseDate = releaseDate,
                        description = description,
                        genre = genre,
                        recordLabel = recordLabel,
                        id = albumId
                    )
                )
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
        return list
    }
}

class NetworkServiceAdapter private constructor(private val context: Context) {

    companion object {
        private const val BASE_URL = "https://appvinilos2023.herokuapp.com/"

        @Volatile
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context): NetworkServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also { instance = it }
            }
        }
    }

    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context.applicationContext) }

    fun createAlbum(
        body: JSONObject,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        createResource("albums", body, onComplete, onError)
    }

    fun createMusician(
        body: JSONObject,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        createResource("musicians", body, onComplete, onError)
    }

    fun createPrize(
        body: JSONObject,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        Log.i("tAG=", "Ingreso a crear Precio");
        createResource("prizes", body, onComplete, onError)
    }

    private fun createResource(
        resourcePath: String,
        body: JSONObject,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest(
                resourcePath,
                body,
                { response -> onComplete(response) },
                { error -> onError(error) }
            )
        )
    }
    suspend fun getPrizes(): List<Prize> = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest(
                "prizes",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Prize>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i, Prize(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                description = item.getString("description"),
                                organization=item.getString("organization")

                            )
                        )
                    }
                    cont.resume(list)
                },
                { error -> cont.resumeWithException(error) }
            )
        )
    }
    suspend fun getAlbums(): List<Album> = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest(
                "albums",
                { response ->
                    val list = FillResponseAlbumTask().execute(response).get()
                    cont.resume(list)
                },
                { error -> cont.resumeWithException(error) }
            )
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(
            Request.Method.GET,
            BASE_URL + path,
            responseListener,
            errorListener
        )
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    fun getMusicians(
        onComplete: (resp: List<Musician>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(getRequest("musicians", { response ->
            val list = FillResponseArtistTask().execute(response).get()
            onComplete(list)
        }, {
            onError(it)

        }))
    }
}

