package com.example.services.user_services

import io.ktor.server.auth.*

data class LoginUserParams(
    val credential: UserPasswordCredential
)