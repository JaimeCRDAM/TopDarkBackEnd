package com.example.utils

import io.ktor.http.*
import kotlinx.serialization.*


@Serializable
sealed class Response<T>(
    @Contextual val statusCode: HttpStatusCode = HttpStatusCode.OK
) {
    @Serializable
    data class SucessResponse<T>(
        val data: T? = null,
        val message: String? = null
    ): Response<T>()

    @Serializable
    data class ErrorResponse<T>(
        val data: T? = null,
        val message: String? = null
    ): Response<T>()
}