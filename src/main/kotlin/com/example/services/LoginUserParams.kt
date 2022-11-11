package com.example.services

import io.ktor.server.auth.*

data class LoginUserParams(
    val credential: UserPasswordCredential
)