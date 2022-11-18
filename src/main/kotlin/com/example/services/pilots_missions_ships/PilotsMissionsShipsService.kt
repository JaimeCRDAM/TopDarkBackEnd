package com.example.services.pilots_missions_ships

import com.example.models.AllData
import com.example.models.Pilot
import com.example.models.Ship

interface PilotsMissionsShipsService {
    suspend fun registerPilotToMissionShip(pilotToMission: PilotToMission): Boolean?

    suspend fun getAllData(id: String): MutableList<AllData>
}