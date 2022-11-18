package com.example.commands.pilots_missions_ships

import com.example.models.AllData
import com.example.models.Mission
import com.example.services.missionservices.MissionService
import com.example.services.missionservices.RegisterMissionParams
import com.example.services.pilots_missions_ships.PilotToMission
import com.example.services.pilots_missions_ships.PilotsMissionsShipsService
import com.example.utils.Response
import io.ktor.http.*

class PilotsMissionsShipsImpl(private val pilotsMissionsShipsService: PilotsMissionsShipsService) : PilotsMissionsShips {
    override suspend fun registerMission(pilotToMission: PilotToMission): Response<Boolean?> {
        val registerMission = pilotsMissionsShipsService.registerPilotToMissionShip(pilotToMission)
        if (registerMission != null){
            return Response(message = "Relation Created", statusCode = HttpStatusCode.OK)
        } else {
            return Response(message = "Nope", statusCode = HttpStatusCode.OK)
        }

    }

    override suspend fun getAllData(id: String): Response<MutableList<AllData>> {
        val combo = pilotsMissionsShipsService.getAllData(id)
        if (combo != null){
            return Response(data = combo, message = "success", statusCode = HttpStatusCode.OK)
        } else {
            return Response(message = "Nope", statusCode = HttpStatusCode.OK)
        }
    }
}