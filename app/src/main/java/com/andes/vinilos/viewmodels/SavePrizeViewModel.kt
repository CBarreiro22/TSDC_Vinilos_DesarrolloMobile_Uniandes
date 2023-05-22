package com.andes.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andes.vinilos.models.Musician
import com.andes.vinilos.models.Prize
import com.andes.vinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError
import org.json.JSONObject
import java.util.Calendar

class SavePrizeViewModel(application: Application) : AndroidViewModel(application)  {
    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)
    fun savePrize(prize: Prize, onSuccess: () -> Unit, onError: () -> Unit) {
        val jsonObject = JSONObject()
        jsonObject.put("name", prize.name)
        jsonObject.put("description", prize.description)
        jsonObject.put("organization", prize.organization)
        networkServiceAdapter.createPrize(jsonObject, {
            Log.d("SavePrizeViewModel", "Prize created successfully")
            onSuccess()
        }, { error: VolleyError ->
            Log.d("SavePrizeViewModel", "Error creating prize: $error")
            onError()
        })
    }

}