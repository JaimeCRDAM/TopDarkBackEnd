package com.example.models.plugins

import com.example.commands.UserCommands
import com.example.services.CreateUserParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerRoute(userCommands: UserCommands) {
    routing {
        route("/auth"){
            post("/register"){
                val params = call.receive<CreateUserParams>()
                val result = userCommands.registerUser(params)
                call.respond(result.statusCode, result)
            }
        }
    }
}
