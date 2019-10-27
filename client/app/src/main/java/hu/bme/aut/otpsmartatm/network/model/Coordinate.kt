package hu.bme.aut.otpsmartatm.network.model

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("x")
    val lat: Double,
    @SerializedName("y")
    val lng: Double
)