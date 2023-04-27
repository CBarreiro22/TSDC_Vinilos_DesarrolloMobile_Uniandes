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

    fun createAlbum(body: JSONObject,  onComplete:(resp:JSONObject)->Unit , onError: (error:VolleyError)->Unit){
        val queue = Volley.newRequestQueue(mContext)




        requestQueue.add(postRequest(
            "albums", body,
            Response.Listener<JSONObject> { response ->
                onComplete(response)
            },
            Response.ErrorListener {
                onError(it)
            }
        ))

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
