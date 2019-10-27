package hu.bme.aut.otpsmartatm.ui.details

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.otpsmartatm.interactor.AtmInteractor
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val atmInteractor: AtmInteractor
) {

    suspend fun getData(): String = withIOContext {
        ""
    }

    suspend fun getAtmById(atmId: String) = withIOContext {
        atmInteractor.getAtmById(atmId)
    }

    suspend fun selectAtm(atmId: String) = withIOContext {
        atmInteractor.selectAtm(atmId)
    }

}
