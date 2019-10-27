package hu.bme.aut.otpsmartatm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.otpsmartatm.data.model.AtmResponse

@Dao
interface AtmDao {
    @Query("SELECT * FROM atmresponse")
    fun getExaminedProductsForDate()
            : List<AtmResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExaminedProduct(atm: AtmResponse)

    @Query("SELECT * FROM atmresponse a WHERE a.lat = :lat AND a.lng = :lng")
    fun getAtmByLatLng(lat: Double, lng: Double): List<AtmResponse>

    @Query("SELECT * FROM atmresponse a WHERE a.id = :id")
    fun getAtmById(id: String): List<AtmResponse>
}
