package com.corradodev.mvvm

import com.corradodev.mvvm.di.AppComponent
import com.corradodev.mvvm.di.AppModule
import com.corradodev.mvvm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}