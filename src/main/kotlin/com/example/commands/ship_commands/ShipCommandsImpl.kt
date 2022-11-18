package com.example.commands.ship_commands

import com.example.models.Mission
import com.example.models.Ship
import com.example.models.objects.Connection
import com.example.services.ship_services.ShipService
import com.example.services.user_services.CreateUserParams
import com.example.utils.Response
import io.ktor.http.*

class ShipCommandsImpl(private val shipService: ShipService) : ShipCommands {
    override suspend fun registerShip(ship: Ship): Response<Boolean?> {
        return if (doShipExist(ship.plate)){
            Response(message = "Ship name already exists", statusCode = HttpStatusCode.OK)
        } else {
            val registerShip = shipService.registerShip(ship)
            if(registerShip != null){
                Response(message = "Ship created successfully", statusCode = HttpStatusCode.OK)
            } else {
                Response(message = "Invalid Data", statusCode = HttpStatusCode.OK)
            }
        }
    }

    override suspend fun getAllShips(): Response<MutableList<Ship>> {
        val result = shipService.getAllShips()
        return if (result?.size == 0)
            Response(message = "No Ship data", statusCode = HttpStatusCode.OK)
        else
            Response(data = result, statusCode = HttpStatusCode.OK)
    }

    private fun doShipExist(plate: String): Boolean{
        return Connection.getShipByName(plate) != null
    }
}