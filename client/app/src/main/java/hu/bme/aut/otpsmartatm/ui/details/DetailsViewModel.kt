package hu.bme.aut.otpsmartatm.ui.details

import co.zsmb.rainbowcake.base.JobViewModel
import co.zsmb.rainbowcake.base.OneShotEvent
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsPresenter: DetailsPresenter
) : JobViewModel<DetailsViewState>(Loading) {

    fun load(atmId: String) = execute {
        viewState = DetailsReady(detailsPresenter.getAtmById(atmId))
    }

    fun selectAtm(atmId: String) = execute {
        detailsPresenter.selectAtm(atmId)

        val atm = detailsPresenter.getAtmById(atmId)

        val pos = LatLng(atm.coordinate.lat, atm.coordinate.lng)

        postEvent(AtmSelected(pos))
    }

    data class AtmSelected(val position: LatLng) : OneShotEvent
}
