package hu.bme.aut.otpsmartatm.di

import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.dagger.RainbowCakeModule
import dagger.Component
import hu.bme.aut.otpsmartatm.data.DiskModule
import hu.bme.aut.otpsmartatm.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RainbowCakeModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        DiskModule::class
    ]
)
interface AppComponent : RainbowCakeComponent