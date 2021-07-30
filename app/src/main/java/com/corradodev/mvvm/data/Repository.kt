package com.corradodev.mvvm.data

import androidx.lifecycle.LiveData

interface Repository<T> {
    fun find(id: Long): LiveData<Resource<T>>
    fun findAll(): LiveData<Resource<List<T>>>
    fun save(data: T, repositoryListener: RepositoryListener)
    fun delete(data: T, repositoryListener: RepositoryListener)
    fun deleteAll(repositoryListener: RepositoryListener)
}