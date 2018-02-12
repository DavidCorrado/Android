package com.corradodev.mvvm.data

import android.arch.lifecycle.MutableLiveData

/**
 * Created by davidcorrado on 11/16/17.
 */

interface FindResult<T> {
    fun findResult(findResult: RepositoryResponse<T>)
}

interface FindAllResult<T> {
    fun findAllResults(findAllResult: RepositoryResponse<List<T>>)
}

interface SaveResult<T> {
    fun saveResult(saveResult: RepositoryResponse<T>)
}

interface DeleteResult {
    fun deleteResult(deleteResult: RepositoryResponse<Unit>)
}

interface DeleteAllResult {
    fun deleteAllResult(deleteAllResult: RepositoryResponse<Unit>)
}

interface RepositoryInterface<T> {
    fun find(id: Long): MutableLiveData<RepositoryResponse<T>>
    fun findAll(): MutableLiveData<RepositoryResponse<List<T>>>
    fun save(data: T)
    fun delete(data: T)
    fun deleteAll()
}

interface RepositoryDatasourceInterface<T> {
    fun find(id: Long, findResult: FindResult<T>? = null)
    fun findAll(findAllResult: FindAllResult<T>? = null)
    fun save(data: T, saveResult: SaveResult<T>? = null)
    fun delete(data: T, deleteResult: DeleteResult? = null)
    fun deleteAll(deleteAllResult: DeleteAllResult? = null)
}