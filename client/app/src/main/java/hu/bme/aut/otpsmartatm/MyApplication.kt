package hu.bme.aut.otpsmartatm

import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import hu.bme.aut.otpsmartatm.di.AppComponent
import hu.bme.aut.otpsmartatm.di.ApplicationModule
import hu.bme.aut.otpsmartatm.di.DaggerAppComponent
import hu.bme.aut.otpsmartatm.network.NetworkModule

class MyApplication : RainbowCakeApplication() {

    override lateinit var injector: AppComponent

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }

    override fun setupInjector() {
        injector =
            DaggerAppComponent
                .builder()
                .networkModule(NetworkModule(applicationContext))
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

}