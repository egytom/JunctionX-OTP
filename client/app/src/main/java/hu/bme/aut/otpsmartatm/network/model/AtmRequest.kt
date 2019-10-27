package hu.bme.aut.otpsmartatm.network.model

import com.google.gson.annotations.SerializedName

data class AtmRequest(
    @SerializedName("coord")
    val coordinate: Coordinate,
    val dayName: String,
    val isDepositRequired: Boolean,
    val section: Int
)