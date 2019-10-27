package hu.bme.aut.otpsmartatm.network

import hu.bme.aut.otpsmartatm.network.model.AtmPreview
import hu.bme.aut.otpsmartatm.network.model.AtmRequest
import hu.bme.aut.otpsmartatm.network.model.AtmResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AtmApi {
    @GET("atm-location")
    suspend fun getAtmLocations(): Response<List<AtmPreview>>

    @GET("selected-atm/{id}")
    suspend fun selectTargetAtm(@Path("id") id: String): ResponseBody

    @POST("all-atm")
    suspend fun getProposedAtms(@Body atm: AtmRequest): Response<List<AtmResponse>>
}