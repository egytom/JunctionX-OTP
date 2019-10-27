package hu.bme.aut.otpsmartatm.data.model

import androidx.room.Entity

@Entity
data class Address(
    val city: String = "",
    val postalCode: String = "",
    val streetAddress: String = ""
)