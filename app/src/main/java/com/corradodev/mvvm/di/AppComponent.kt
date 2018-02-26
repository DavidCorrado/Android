package com.corradodev.mvvm.di

import com.corradodev.mvvm.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBindingModule::class)])
interface AppComponent : AndroidInjector<App>