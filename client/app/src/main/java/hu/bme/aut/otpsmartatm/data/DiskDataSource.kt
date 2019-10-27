package hu.bme.aut.otpsmartatm.data

import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.otpsmartatm.data.model.AtmResponse
import javax.inject.Inject

class DiskDataSource @Inject constructor(
    private val dao: AtmDao
) {
    suspend fun saveAtms(atms: List<AtmResponse>) {
        atms.forEach {
            dao.insertExaminedProduct(it)
        }
    }

    suspend fun getAtms(): List<AtmResponse> {
        return dao.getExaminedProductsForDate()
    }

    suspend fun getAtmByLatLng(latLng: LatLng): AtmResponse {
        return dao.getAtmByLatLng(latLng.latitude, latLng.longitude).first()
    }

    suspend fun getAtmById(id: String): AtmResponse {
        return dao.getAtmById(id).first()
    }
}