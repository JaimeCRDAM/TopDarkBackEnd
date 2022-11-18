package com.example.models.plugins

import com.example.commands.ship_commands.ShipCommands
import com.example.commands.user_commands.UserCommands
import com.example.models.Globals.ADMIN_ROLE
import com.example.models.Ship
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerShip(shipCommands: ShipCommands) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                post("/register/ship") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == ADMIN_ROLE) {
                        val params = call.receive<Ship>()
                        val result = shipCommands.registerShip(params)
                        call.respond(result.statusCode, result)
                        return@post
                    }
                    call.respondText("You are not an admin")
                }
            }
        }
    }
}
