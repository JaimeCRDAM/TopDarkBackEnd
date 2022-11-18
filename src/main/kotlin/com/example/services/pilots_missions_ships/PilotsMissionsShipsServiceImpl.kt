package com.example.services.pilots_missions_ships

import com.example.models.AllData
import com.example.models.Mission
import com.example.models.Ship
import com.example.models.objects.Connection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class PilotsMissionsShipsServiceImpl : PilotsMissionsShipsService {
    override suspend fun registerPilotToMissionShip(pilotToMission: PilotToMission): Boolean? {
        val ships = Connection.getCollection("ships")
        val missions = Connection.getCollection("missions")
        val ship = ships.find().first { it["plate"] == pilotToMission.shipId }
        val mission = missions.find().first { it["_id"].toString() == pilotToMission.missionId }

        if ( ship == null || ship["owner"] != "") {
            return null
        }
        if (mission == null || mission["owner"] != "") {
            return null
        }
        ships.findOneAndUpdate(
            Filters.eq("plate", pilotToMission.shipId),
            Updates.set("owner", pilotToMission.pilotId)
        )
        val id = ObjectId(pilotToMission.missionId)
        missions.findOneAndUpdate(
            Filters.eq("_id", id),
            Updates.set("owner", pilotToMission.pilotId)
        )
        return true
    }

    override suspend fun getAllData(id: String): MutableList<AllData> {

        val combo: MutableList<AllData> = mutableListOf()
        val ships = Connection.getCollection("ships").find().filter{it["owner"] == id}.map {
            documentToShip(it)
        }
        val missions = Connection.getCollection("missions").find().filter{it["owner"] == id}.map {
            documentToMission(it)
        }
        val hashmap = hashMapOf<String, String>()
        hashmap["Fighters"] = "Combat"
        hashmap["Shuttles"] = "Flight"
        hashmap["Bombers"] = "Bombardment"
        ships.forEach {ship ->
            missions.forEach {
                mission ->
                //println(hashmap[ship.type])
                //println(mission.missionType)
                if (hashmap[ship.type] == mission.missionType){
                    combo.add((AllData(mission, ship)))
                }

            }
        }
        println(combo)
        return combo
    }

    private fun documentToMission(document: Document): Mission{
        return Mission(
            _id = document["_id"].toString(),
            amount = document["amount"] as Int,
            firstCheck = document["firstCheck"] as Boolean,
            secondCheck = document["secondCheck"] as Boolean,
            missionType = document["missionType"] as String,
        )
    }
    fun documentToShip(document: Document):Ship {
        return Ship(
            plate = document["plate"] as String,
            type = document["type"] as String ,
            avatar = document["avatar"] as String,
            firstCheck = document["firstcheck"] as Boolean,
            secondCheck = document["secondcheck"] as Boolean
        )
    }


}