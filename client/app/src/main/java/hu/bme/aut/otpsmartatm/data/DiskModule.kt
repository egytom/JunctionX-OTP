package hu.bme.aut.otpsmartatm.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DiskModule {
    companion object {
        private const val DB_NAME = "qmonitor-db"
    }

    @Provides
    @Singleton
    fun provideQMonitorDatabase(context: Context): OtpAtmDatabase {
        return Room.databaseBuilder(context, OtpAtmDatabase::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideExaminedProductDao(otpAtmDatabase: OtpAtmDatabase): AtmDao = otpAtmDatabase.atmDao()
}
