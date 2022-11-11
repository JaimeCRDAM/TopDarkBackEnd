package com.example.routes.authroutes

import com.example.security.UserIdPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.login(){
    routing {
        authenticate("auth-basic") {
            route("/auth"){
                get("/userpassword") {
                    val result = call.principal<UserIdPrincipal>()!!
                    call.respond(result.response.statusCode, result.response)
                }
            }
        }
    }
}