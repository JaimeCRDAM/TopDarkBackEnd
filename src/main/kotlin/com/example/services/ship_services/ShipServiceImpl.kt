package com.example.services.ship_services

import com.example.models.Globals
import com.example.models.Mission
import com.example.models.Ship
import com.example.models.User
import com.example.models.objects.Connection
import com.example.security.JwtConfig
import com.example.security.hash
import com.example.services.missionservices.RegisterMissionParams
import com.example.utils.Response
import com.mongodb.client.result.InsertOneResult
import io.ktor.http.*
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class ShipServiceImpl : ShipService {
    override suspend fun registerShip(ship: Ship): Ship? {
        val shipDocument = Document()
        var resultDocument: Document? = null
        Connection.dbQuery {
            shipDocument
                .append("plate", ship.plate)
                .append("avatar", ship.avatar)
                .append("firstcheck", ship.firstCheck)
                .append("type", ship.type)
                .append("secondcheck", ship.secondCheck)
                .append("owner", "")

            Connection.insertIntoTable("usuarios", "ships", shipDocument)
        }
        resultDocument = Connection.getShipByName(ship.plate)

        return documentToRegisterShip(resultDocument)
    }

    fun documentToRegisterShip(document: Document?):Ship? {
        return if(document == null) null
        else {
            Ship(
                plate = document["plate"] as String,
                type = document["type"] as String ,
                avatar = document["avatar"] as String,
                firstCheck = document["firstcheck"] as Boolean, //Can cargo
                secondCheck = document["secondcheck"] as Boolean
            )
        }
    }

    fun documentToShip(document: Document):Ship? {
        if (document["owner"]!= "") return null
        return Ship(
            plate = document["plate"] as String,
            type = document["type"] as String ,
            avatar = document["avatar"] as String,
            firstCheck = document["firstcheck"] as Boolean, //Can cargo
            secondCheck = document["secondcheck"] as Boolean
        )
    }

    override suspend fun getAllShips(): MutableList<Ship>? {
        val resultQuery = Connection.getAllShips() ?: return null
        val ships2: MutableList<Ship> = mutableListOf()
        resultQuery.forEach {
                if(documentToShip(it) != null){
                    ships2.add(documentToShip(it)!!)
                }
        }
        return ships2.toMutableList()
    }
}