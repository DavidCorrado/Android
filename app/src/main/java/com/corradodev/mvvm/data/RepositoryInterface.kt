package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData

/**
 * Created by davidcorrado on 11/16/17.
 */

interface RepositoryInterface<T> {
    fun find(id: Long): LiveData<T>
    fun findAll(): LiveData<List<T>>
    fun save(t: T)
    fun delete(t: T)
}