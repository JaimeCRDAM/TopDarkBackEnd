package com.example.security

import com.example.commands.UserCommands
import com.example.services.LoginUserParams
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

fun Application.configureSecurity(userCommands: UserCommands) {
    JwtConfig.init("vamos")
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.instance.verifier)
            validate {
                if (it.expiresAt!! > Date() && it.payload.getClaim(JwtConfig.CLAIM).asString() != "") {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
        basic("auth-basic"){
            validate { credentials ->
                val result = userCommands.loginUser(LoginUserParams(credentials))
                UserIdPrincipal(result)
            }
        }
    }
}