package com.corradodev.mvvm

import android.app.Application
import com.corradodev.mvvm.di.AppComponent
import com.corradodev.mvvm.di.AppModule
import com.corradodev.mvvm.di.DaggerAppComponent

/**
 * Created by davidcorrado on 11/17/17.
 */

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }
}