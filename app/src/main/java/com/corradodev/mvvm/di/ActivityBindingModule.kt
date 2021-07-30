package com.corradodev.mvvm.di

import androidx.lifecycle.ViewModelProvider
import com.corradodev.mvvm.ui.TaskEditActivity
import com.corradodev.mvvm.ui.TaskListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(TaskListActivityModule::class))
    internal abstract fun taskListActivity(): TaskListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(TaskEditActivityModule::class))
    internal abstract fun taskEditActivity(): TaskEditActivity
}