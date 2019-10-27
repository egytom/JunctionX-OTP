package hu.bme.aut.otpsmartatm.interactor

import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.otpsmartatm.data.DiskDataSource
import hu.bme.aut.otpsmartatm.data.model.Address
import hu.bme.aut.otpsmartatm.data.model.Coordinate
import hu.bme.aut.otpsmartatm.network.NetworkDataSource
import hu.bme.aut.otpsmartatm.network.model.AtmPreview
import hu.bme.aut.otpsmartatm.network.model.AtmRequest
import hu.bme.aut.otpsmartatm.network.model.AtmResponse
import javax.inject.Inject

class AtmInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val diskDataSource: DiskDataSource
) {
    suspend fun getAtms(): List<AtmPreview> {
        return networkDataSource.getAtmLocations()
    }

    suspend fun sendData(atmReq: AtmRequest): List<AtmResponse> {
        val atms = networkDataSource.getProposedAtms(atmReq)
        diskDataSource.saveAtms(atms.map { atm ->
            hu.bme.aut.otpsmartatm.data.model.AtmResponse(
                atm.address.let { Address(it.city, it.postalCode, it.streetAddress) },
                atm.coordinate.let { Coordinate(it.lat, it.lng) },
                atm.countOfFutureCustomers,
                atm.date,
                atm.day,
                atm.id,
                atm.isDepositAvailable,
                atm.queueAndTravelTime
            )
        })
        return atms
    }

    suspend fun selectAtm(atmId: String) {
        networkDataSource.selectTargetAtm(atmId)
    }

    suspend fun getAtmyLatLng(latLng: LatLng): hu.bme.aut.otpsmartatm.data.model.AtmResponse {
        return diskDataSource.getAtmByLatLng(latLng)
    }

    suspend fun getAtmById(id: String): hu.bme.aut.otpsmartatm.data.model.AtmResponse {
        return diskDataSource.getAtmById(id)
    }

}
