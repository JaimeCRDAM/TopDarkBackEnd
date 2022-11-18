package com.example.routes.get.all_data

import com.example.commands.mission_commands.MissionCommands
import com.example.commands.pilots_missions_ships.PilotsMissionsShips
import com.example.models.Globals
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getAllData(pilotsMissionsShips: PilotsMissionsShips) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                get("/allData/{id}") {
                    val id = call.parameters["id"]!!
                    val result = pilotsMissionsShips.getAllData(id)
                    call.respond(result.statusCode, result)
                    return@get
                }
            }
        }
    }
}