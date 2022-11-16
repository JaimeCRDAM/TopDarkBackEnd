package com.example.services.userservices

import io.ktor.server.auth.*

data class LoginUserParams(
    val credential: UserPasswordCredential
)