package com.example.services.missionservices

import com.example.models.Mission
import com.example.utils.Response


interface MissionService {
    suspend fun registerMission(params: RegisterMissionParams, missionType: String): RegisterMission?

    suspend fun getAllMissions(): MutableList<Mission>?
}