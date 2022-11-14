package com.example.security

import com.auth0.jwt.interfaces.Payload
import com.example.models.User
import com.example.utils.Response
import io.ktor.server.auth.*

data class UserIdPrincipal(
    val response: Response<User?>? = null,
    val customPayLoad: Payload? = null,
    val role: String? = null
) : Principal