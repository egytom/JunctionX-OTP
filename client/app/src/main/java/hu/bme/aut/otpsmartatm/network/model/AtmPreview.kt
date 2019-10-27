package hu.bme.aut.otpsmartatm.network.model

import com.google.gson.annotations.SerializedName

data class AtmPreview(
    @SerializedName("coord")
    val coordinate: Coordinate,
    val id: String,
    val isDepositAvailable: Boolean,
    val queueAndTravelTime: String
)