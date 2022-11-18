package com.example.routes.get.missions

import com.example.commands.mission_commands.MissionCommands
import com.example.commands.pilots_missions_ships.PilotsMissionsShips
import com.example.models.Globals
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getMissions(missionCommands: MissionCommands) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                get("/missions") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == Globals.ADMIN_ROLE) {
                        val result = missionCommands.getAllMissions()
                        call.respond(result.statusCode, result)
                        return@get
                    }
                    val result = Response<Boolean>(message ="You are not an admin", statusCode = HttpStatusCode.Unauthorized )
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}