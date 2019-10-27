package hu.bme.aut.otpsmartatm.data.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class AtmResponse(
    @Embedded
    val address: Address,
    @Embedded
    val coordinate: Coordinate,
    val countOfFutureCustomers: Int,
    val date: String,
    val day: String,
    val id: String,
    val isDepositAvailable: Boolean,
    val queueAndTravelTime: String
)