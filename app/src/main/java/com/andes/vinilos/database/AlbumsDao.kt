package com.andes.vinilos.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andes.vinilos.models.Album

@Dao
interface AlbumsDao {

    @Query("SELECT * FROM album")
    fun getAlbums():List<Album>


   /* @Query("DELETE FROM album")
    suspend fun deleteAll(): Int*/
}