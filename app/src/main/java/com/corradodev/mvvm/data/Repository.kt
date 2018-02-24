package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData

interface Repository<T> {
    fun find(id: Long): LiveData<Resource<T>>
    fun findAll(): LiveData<Resource<List<T>>>
    fun save(data: T, response: RepositoryListener)
    fun delete(data: T, response: RepositoryListener)
    fun deleteAll(response: RepositoryListener)
}