package hu.bme.aut.otpsmartatm.network.model

import com.google.gson.annotations.SerializedName

data class AtmResponse(
    val address: Address,
    @SerializedName("coord")
    val coordinate: Coordinate,
    val countOfFutureCustomers: Int,
    val date: String,
    val day: String,
    val id: String,
    val isDepositAvailable: Boolean,
    val queueAndTravelTime: String
)