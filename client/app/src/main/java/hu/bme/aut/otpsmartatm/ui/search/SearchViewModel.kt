package hu.bme.aut.otpsmartatm.ui.search

import co.zsmb.rainbowcake.base.JobViewModel
import co.zsmb.rainbowcake.base.OneShotEvent
import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.otpsmartatm.network.model.AtmPreview
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchPresenter: SearchPresenter
) : JobViewModel<SearchViewState>(Loading) {

    fun load() = execute {
        viewState = SearchReady(searchPresenter.getAtms())
    }

    fun sendData(currentPos: LatLng, offset: Int, checked: Boolean) = execute {
        val result = searchPresenter.sendData(currentPos, offset, checked)

        postEvent(SearchResultReady(result))
    }

    suspend fun selectAtm(atmId: String) = execute {
        searchPresenter.selectAtm(atmId)
    }

    fun getAtmByLatLng(position: LatLng) = execute {
        val atm = searchPresenter.getAtmByLatLng(position)

        postEvent(AtmFound(atm.id))
    }

    data class SearchResultReady(val atms: List<AtmPreview>) : OneShotEvent

    data class AtmFound(val id: String) : OneShotEvent
}