package com.example.commands.ship_commands

import com.example.models.Ship
import com.example.models.User
import com.example.services.user_services.CreateUserParams
import com.example.utils.Response

interface ShipCommands {

    suspend fun registerShip(ship: Ship): Response<Boolean?>

    suspend fun getAllShips(): Response<MutableList<Ship>>

}