package hu.bme.aut.otpsmartatm.ui.details

import hu.bme.aut.otpsmartatm.data.model.AtmResponse

sealed class DetailsViewState

object Loading : DetailsViewState()

data class DetailsReady(val data: AtmResponse) : DetailsViewState()
