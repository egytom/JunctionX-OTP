package hu.bme.aut.otpsmartatm.ui.search

import hu.bme.aut.otpsmartatm.network.model.AtmPreview

sealed class SearchViewState

object Loading : SearchViewState()

data class SearchReady(val atms: List<AtmPreview>) : SearchViewState()
