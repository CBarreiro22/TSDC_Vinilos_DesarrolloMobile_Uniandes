package com.andes.vinilos.network

import android.content.Context
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.NewAlbum
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

    fun createAlbum(body: JSONObject, onComplete: (resp: JSONObject) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            postRequest(
                "albums",
                body,
                Response.Listener<JSONObject> { response -> onComplete(response) },
                Response.ErrorListener { error -> onError(error) }
            )
        )
    }

    fun createMusician(body: JSONObject, albumId: Int, onComplete: (resp: JSONObject) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            postRequest(
                "musicians",
                body,
                Response.Listener<JSONObject> { response -> onComplete(response) },
                Response.ErrorListener { error -> onError(error) }
            )
        )
    }

    suspend fun getAlbums(): List<NewAlbum> = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest(
                "albums",
                Response.Listener<String> { response ->
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
                    cont.resume(list)
                },
                Response.ErrorListener { error -> cont.resumeWithException(error) }
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
}

