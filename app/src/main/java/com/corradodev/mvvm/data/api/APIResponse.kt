package com.corradodev.mvvm.data.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import retrofit2.Response
import java.io.IOException

class APIResponse<T> : AnkoLogger {
    private val code: Int
    val body: T?
    var errorMessage: String? = null
    val isSuccessful: Boolean
        get() = code in 200..299

    constructor(error: Throwable) {
        code = 500
        body = null
        if (error is IOException) {
            errorMessage = "Please check your internet connection."
        }
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
        } else {
            try {
                errorMessage = Gson().fromJson(response.errorBody().toString(), JsonObject::class.java).get("message").asString
            } catch (e: Exception) {
                warn("Could not parse error message")
            }
            body = null
        }
    }
}