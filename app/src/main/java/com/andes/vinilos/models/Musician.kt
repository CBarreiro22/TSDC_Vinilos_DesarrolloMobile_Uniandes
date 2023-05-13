package com.andes.vinilos.models

data class Musician(
    val id: Int? = null,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: MutableList<Album>? = null
)