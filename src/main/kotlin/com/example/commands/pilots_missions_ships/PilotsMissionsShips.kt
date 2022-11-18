package com.example.commands.pilots_missions_ships


import com.example.models.AllData
import com.example.models.Mission
import com.example.services.missionservices.RegisterMissionParams
import com.example.services.pilots_missions_ships.PilotToMission
import com.example.utils.Response

interface PilotsMissionsShips {
    suspend fun registerMission(pilotToMission: PilotToMission): Response<Boolean?>

    suspend fun getAllData(id: String): Response<MutableList<AllData>>
}