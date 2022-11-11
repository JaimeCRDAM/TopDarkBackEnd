package com.example.utils

import io.ktor.http.*



data class Response<T>(
    val data: T? = null,
    val message: String? = null,
    var statusCode: HttpStatusCode = HttpStatusCode.OK
)

