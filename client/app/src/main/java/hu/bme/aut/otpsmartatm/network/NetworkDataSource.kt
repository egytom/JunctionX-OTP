package hu.bme.aut.otpsmartatm.network

import hu.bme.aut.otpsmartatm.network.model.AtmPreview
import hu.bme.aut.otpsmartatm.network.model.AtmRequest
import hu.bme.aut.otpsmartatm.network.model.AtmResponse
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val atmApi: AtmApi
) {
    suspend fun getAtmLocations(): List<AtmPreview> {
        return try {
            val response = atmApi.getAtmLocations()
            response.body() ?: emptyList()
        } catch (t: Throwable) {
            t.printStackTrace()
            emptyList()
        }
    }

    suspend fun selectTargetAtm(atmId: String) {
        try {
            atmApi.selectTargetAtm(atmId)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    suspend fun getProposedAtms(atmRequest: AtmRequest): List<AtmResponse> {
        return try {
            val response = atmApi.getProposedAtms(atmRequest)
            response.body()!!
        } catch (t: Throwable) {
            emptyList()
        }
    }
}