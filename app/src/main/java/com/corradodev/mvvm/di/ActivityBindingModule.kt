package com.corradodev.mvvm.di

import android.arch.lifecycle.ViewModelProvider
import com.corradodev.mvvm.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by davidcorrado on 12/6/17.
 */

@Module
abstract class ActivityBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun mainActivity(): MainActivity
}