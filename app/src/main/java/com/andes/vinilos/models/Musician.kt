package com.andes.vinilos.models

data class Musician(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate:String,
    val albums: MutableList<NewAlbum>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Musician

        if (id != other.id) return false
        if (name != other.name) return false
        if (image != other.image) return false
        if (description != other.description) return false
        if (birthDate != other.birthDate) return false


        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + birthDate.hashCode()

        return result
    }
}
