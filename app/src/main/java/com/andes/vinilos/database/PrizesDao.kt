package com.andes.vinilos.database

import androidx.room.Dao
import androidx.room.Query
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.Prize

@Dao
interface PrizesDao {
    @Query("SELECT * FROM prize")
    fun getPrizes():List<Prize>
}