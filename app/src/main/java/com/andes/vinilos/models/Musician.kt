package com.andes.vinilos.models

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Musician(
    val id: Int? = null,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String?,
    val albums: MutableList<Album>? = null
){
    fun getFormattedBirthDate(): String {
        if (birthDate != null) {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            try {
                val date = inputDateFormat.parse(birthDate)
                return outputDateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return ""
    }
}
