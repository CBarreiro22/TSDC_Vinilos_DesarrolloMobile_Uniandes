package com.andes.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.andes.vinilos.database.PrizesDao
import com.andes.vinilos.models.Prize
import com.andes.vinilos.network.NetworkServiceAdapter

class PrizeRepository(val application: Application, private val prizesDao: PrizesDao) {
    suspend fun refreshData(): List<Prize> {
        var cached = prizesDao.getPrizes()
        return cached.ifEmpty {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getPrizes()
        }
    }
}