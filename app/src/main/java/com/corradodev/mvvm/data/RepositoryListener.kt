package com.corradodev.mvvm.data

interface RepositoryListener {
    fun response(response: Resource<Unit>)
}