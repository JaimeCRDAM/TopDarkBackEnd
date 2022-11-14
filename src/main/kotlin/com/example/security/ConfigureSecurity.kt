package com.example.security

import com.example.commands.UserCommands
import com.example.services.LoginUserParams
import com.example.services.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

fun Application.configureSecurity(userCommands: UserCommands, userService: UserService) {
    JwtConfig.init("vamos")
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.instance.verifier)
            validate {
                val userName = it.payload.getClaim(JwtConfig.CLAIM).asString()
                val role = userService.findRoleByUserName(userName)
                if (it.expiresAt!! > Date() && userName != "") {
                    val response = userCommands.findUserByName(userName)
                    UserIdPrincipal(response, customPayLoad = it.payload, role)
                } else {
                    null
                }
            }
        }

        basic("auth-basic"){
            validate { credentials ->
                val result = userCommands.loginUser(LoginUserParams(credentials))
                UserIdPrincipal(result, null)
            }
        }
    }
}