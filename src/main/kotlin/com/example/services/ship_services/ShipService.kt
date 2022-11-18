package com.example.services.ship_services

import com.example.models.Mission
import com.example.models.Ship
import com.example.services.missionservices.RegisterMissionParams
import com.example.utils.Response

interface ShipService {
    suspend fun registerShip(ship: Ship): Ship?

    suspend fun getAllShips(): MutableList<Ship>?
}