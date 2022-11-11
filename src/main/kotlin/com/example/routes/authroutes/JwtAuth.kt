package com.example.models.plugins

import com.example.security.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.jwtAuth() {
    routing {
        authenticate("auth-jwt") {
            route("/auth"){
                get("/jwtAuth") {
                    val result = call.principal<com.example.security.UserIdPrincipal>()!!
                    call.respond(result.response!!.statusCode, result.response)
                }
            }
        }
    }
}