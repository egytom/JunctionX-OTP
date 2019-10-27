package hu.bme.aut.otpsmartatm.model

import com.google.android.gms.maps.model.LatLng

data class Atm(
    val pos: LatLng,
    val address: String,
    val deposit: Boolean
)