package hu.bme.aut.otpsmartatm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.otpsmartatm.data.model.AtmResponse

@Database(entities = [AtmResponse::class], version = 1)
abstract class OtpAtmDatabase : RoomDatabase() {
    abstract fun atmDao(): AtmDao
}
