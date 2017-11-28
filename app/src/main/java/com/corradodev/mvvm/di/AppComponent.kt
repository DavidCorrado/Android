package com.corradodev.mvvm.di

import com.corradodev.mvvm.App
import com.corradodev.mvvm.ui.MainActivity
import com.corradodev.mvvm.ui.TasksViewModel
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by davidcorrado on 11/17/17.
 */

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class))
interface AppComponent : AndroidInjector<App> {
    fun inject(mainActivity: MainActivity)
    fun inject(taskViewModel: TasksViewModel)
}