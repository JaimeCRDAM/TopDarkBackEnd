package com.example.models.plugins

import com.example.commands.UserCommands
import com.example.models.Globals.ADMIN_ROLE
import com.example.services.CreateUserParams
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerRoute(userCommands: UserCommands) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                post("/register") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == ADMIN_ROLE) {
                        val params = call.receive<CreateUserParams>()
                        val result = userCommands.registerUser(params)
                        call.respond(result.statusCode, result)
                        return@post
                    }
                    call.respondText("You are not an admin")
                }
            }
        }
    }
}
