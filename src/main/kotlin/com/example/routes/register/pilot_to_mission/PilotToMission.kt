package com.example.routes.register.pilot_to_mission

import com.example.commands.pilots_missions_ships.PilotsMissionsShips
import com.example.models.Globals
import com.example.services.pilots_missions_ships.PilotToMission
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerPilotToMission(pilotsMissionsShips: PilotsMissionsShips) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                post("/register/pilottomission") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == Globals.ADMIN_ROLE) {
                        val generalDataClass = call.receive<PilotToMission>()
                        val result = pilotsMissionsShips.registerMission(generalDataClass)
                        call.respond(result.statusCode, result)
                        return@post
                    }
                    val result = Response<Boolean?>(message ="You are not an admin", statusCode = HttpStatusCode.Unauthorized )
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}