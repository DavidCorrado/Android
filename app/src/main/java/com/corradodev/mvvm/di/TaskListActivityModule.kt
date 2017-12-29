package com.corradodev.mvvm.di

import android.arch.lifecycle.ViewModel
import com.corradodev.mvvm.ui.TasksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by davidcorrado on 12/6/17.
 */

@Module()
abstract class TaskListActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(TasksViewModel::class)
    abstract fun bindViewModel(viewModel: TasksViewModel): ViewModel
}