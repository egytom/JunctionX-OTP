package hu.bme.aut.otpsmartatm.data.model

import androidx.room.Entity

@Entity
data class Coordinate(
    val lat: Double,
    val lng: Double
)