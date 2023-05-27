package com.andes.vinilos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.Prize


@Database(entities = [Album::class, Prize::class], version = 1, exportSchema = false)
abstract class VinilosRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun prizesDao(): PrizesDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinilosRoomDatabase? = null

        fun getDatabase(context: Context): VinilosRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinilosRoomDatabase::class.java,
                    "vinilos_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}