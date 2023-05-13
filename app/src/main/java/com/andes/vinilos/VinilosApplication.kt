package com.andes.vinilos

import android.app.Application
import com.andes.vinilos.database.VinilosRoomDatabase


class VinilosApplication: Application()  {
    val database by lazy { VinilosRoomDatabase.getDatabase(this) }
}