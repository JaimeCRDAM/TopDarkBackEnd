package com.example.models.plugins

import com.example.security.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.jwtAuth() {
    routing {
        authenticate("auth-jwt") {
            route("/auth"){
                get("/jwtAuth") {
                    val principal = call.principal<JWTPrincipal>()
                    val username = principal!!.payload.getClaim(JwtConfig.CLAIM).asString()
                    call.respond(UserIdPrincipal(username))
                }
            }
        }
    }
}