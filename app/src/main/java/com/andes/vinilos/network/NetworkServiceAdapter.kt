package com.andes.vinilos.network

import android.content.Context
import com.andes.vinilos.models.Album
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.google.gson.Gson
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import javax.xml.transform.ErrorListener

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
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
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

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(getRequest("albums", { response ->
            val resp = JSONArray(response)
            val list = mutableListOf<Album>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                list.add(
                    i, Album(
                        albumId = item.getInt("id"),
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


}
