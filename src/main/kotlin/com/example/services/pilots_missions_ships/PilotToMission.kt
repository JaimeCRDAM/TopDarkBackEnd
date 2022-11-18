package com.example.services.pilots_missions_ships

data class PilotToMission(
    val pilotId: String,
    val missionId: String,
    val shipId: String
)