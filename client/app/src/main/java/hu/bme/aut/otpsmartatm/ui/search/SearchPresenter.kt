package hu.bme.aut.otpsmartatm.ui.search

import co.zsmb.rainbowcake.withIOContext
import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.otpsmartatm.interactor.AtmInteractor
import hu.bme.aut.otpsmartatm.network.model.AtmPreview
import hu.bme.aut.otpsmartatm.network.model.AtmRequest
import hu.bme.aut.otpsmartatm.network.model.Coordinate
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val atmInteractor: AtmInteractor
) {

    suspend fun getAtms() = withIOContext {
        atmInteractor.getAtms()
    }

    suspend fun sendData(currentPos: LatLng, offset: Int, checked: Boolean) = withIOContext {
        val atmReq = AtmRequest(
            coordinate = Coordinate(currentPos.latitude, currentPos.longitude),
            dayName = "",
            isDepositRequired = checked,
            section = offset
        )
        atmInteractor.sendData(atmReq).map {
            AtmPreview(
                coordinate = it.coordinate,
                isDepositAvailable = it.isDepositAvailable,
                id = it.id,
                queueAndTravelTime = it.queueAndTravelTime
            )
        }
    }

    suspend fun selectAtm(atmId: String) = withIOContext {
        atmInteractor.selectAtm(atmId)
    }

    suspend fun getAtmByLatLng(position: LatLng) = withIOContext {
        atmInteractor.getAtmyLatLng(position)
    }
}
