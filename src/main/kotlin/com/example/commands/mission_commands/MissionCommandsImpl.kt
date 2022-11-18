package com.example.commands.mission_commands

import com.example.models.Mission
import com.example.models.Ship
import com.example.services.missionservices.MissionService
import com.example.services.missionservices.RegisterMissionParams
import com.example.services.pilots_missions_ships.PilotToMission
import com.example.services.pilots_missions_ships.PilotsMissionsShipsService
import com.example.utils.Response
import io.ktor.http.*

class MissionCommandsImpl(private val missionService: MissionService) : MissionCommands {
    override suspend fun registerMission(registerMissionParams: RegisterMissionParams, type: String): Response<Boolean?> {
        val registerMission = missionService.registerMission(registerMissionParams, type)
        if (registerMission != null){
            return Response(message = "Relation Created", statusCode = HttpStatusCode.OK)
        } else {
            return Response(message = "Nope", statusCode = HttpStatusCode.OK)
        }

    }

    override suspend fun getAllMissions(): Response<MutableList<Mission>> {
        val result = missionService.getAllMissions()
        return if (result?.size == 0)
            Response(message = "No Ship data", statusCode = HttpStatusCode.OK)
        else
            Response(data = result, statusCode = HttpStatusCode.OK)

    }
}