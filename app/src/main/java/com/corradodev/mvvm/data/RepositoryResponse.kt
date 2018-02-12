package com.corradodev.mvvm.data

/**
 * Created by davidcorrado on 2/10/18.
 */

data class RepositoryResponse<T>(var data: T? = null, var status: StatusType = StatusType.SUCCESS, var errorMessage: String = "")

enum class StatusType {
    SUCCESS, GENERIC_ERROR, NO_INTERNET, UNAUTHORIZED, LOADING
}