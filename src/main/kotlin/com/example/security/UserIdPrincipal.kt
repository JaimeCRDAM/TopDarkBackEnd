package com.example.security

import com.example.models.User
import com.example.utils.Response
import io.ktor.server.auth.*

data class UserIdPrincipal(val response: Response<User?>):Principal