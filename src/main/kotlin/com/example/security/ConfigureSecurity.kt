package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    JwtConfig.init("vamos")
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.instance.verifier)
            validate {
                if (it.payload.getClaim(JwtConfig.CLAIM).asString() != "") {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
    }
}