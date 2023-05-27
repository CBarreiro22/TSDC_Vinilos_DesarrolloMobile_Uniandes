package com.andes.vinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prize")
data class Prize (
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val organization: String
)