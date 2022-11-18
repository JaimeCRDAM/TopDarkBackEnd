package com.example.commands.mission_commands


import com.example.models.Mission
import com.example.services.missionservices.RegisterMissionParams
import com.example.services.pilots_missions_ships.PilotToMission
import com.example.utils.Response

interface MissionCommands {
    suspend fun registerMission(registerMissionParams: RegisterMissionParams, type: String): Response<Boolean?>

    suspend fun getAllMissions(): Response<MutableList<Mission>>
}