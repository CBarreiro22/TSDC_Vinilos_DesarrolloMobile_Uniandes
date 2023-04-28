package com.andes.vinilos.network

import android.content.Context
import android.util.Log
import com.andes.vinilos.models.Album
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.google.gson.Gson


class NetworkServiceAdapter constructor(context: Context) {
    private val mContext: Context = context.applicationContext

    companion object {
        const val BASE_URL = "https://appvinilos2023.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.

        Volley.newRequestQueue(mContext)
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


}
