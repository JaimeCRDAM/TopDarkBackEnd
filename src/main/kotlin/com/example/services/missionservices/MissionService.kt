package com.example.services.missionservices

import com.example.utils.Response

interface MissionService {
    suspend fun registerMission(params: RegisterMissionParams, missionType: String): Response<Boolean?>
}