package com.example.routes.register.mission

import com.example.commands.mission_commands.MissionCommands
import com.example.services.missionservices.RegisterMissionParams
import com.example.models.Globals
import com.example.services.missionservices.MissionService
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerMission(missionCommands: MissionCommands) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                post("/register/mission/{id}") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == Globals.ADMIN_ROLE) {
                        val generalDataClass = call.receive<RegisterMissionParams>()
                        val missionType = call.parameters["id"]!!
                        val result = missionCommands.registerMission(generalDataClass,missionType)
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