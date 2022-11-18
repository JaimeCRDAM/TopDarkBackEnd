package com.example.services.missionservices


import com.example.models.Mission
import com.example.models.Ship
import com.example.models.objects.Connection
import com.example.models.objects.Connection.dbQuery
import com.mongodb.client.result.InsertOneResult
import org.bson.Document
import org.bson.types.ObjectId

class MissionServiceImpl : MissionService {
    override suspend fun registerMission(params: RegisterMissionParams, missionType: String): RegisterMission? {
        lateinit var statement: InsertOneResult
        val userDocument = Document()
        lateinit var insertedId: ObjectId
        var resultDocument: Document? = null
        dbQuery {
            userDocument
                .append("amount", params.amount)
                .append("firstCheck", params.firstCheck)
                .append("secondCheck", params.secondCheck)
                .append("missionType", missionType)
                .append("owner", "")

            statement = Connection.insertIntoTable("usuarios", "missions", userDocument)
            insertedId = statement.insertedId!!.asObjectId().value
        }
        resultDocument = Connection.getDocumentById(table = "missions", id = insertedId)

        return documentToRegisterMission(resultDocument)
    }

    override suspend fun getAllMissions(): MutableList<Mission>? {
        val resultQuery = Connection.getAllMission() ?: return null
        val ships2: MutableList<Mission> = mutableListOf()
        resultQuery.forEach {
            if(documentToMission(it) != null){
                ships2.add(documentToMission(it)!!)
            }
        }
        return ships2.toMutableList()
    }

    private fun documentToRegisterMission(document: Document?): RegisterMission? {
        return if(document == null) null
        else {
            RegisterMission(
                RegisterMissionParams(
                    amount = document["amount"] as Int,
                    firstCheck = document["firstCheck"] as Boolean ,
                    secondCheck = document["secondCheck"] as Boolean,
                ),
                missionType = document["missionType"] as String
            )
        }
    }

    private fun documentToMission(document: Document): Mission?{
        if(document["owner"]!="")return null
       return Mission(
            _id = document["_id"].toString(),
            amount = document["amount"] as Int,
            firstCheck = document["firstCheck"] as Boolean,
            secondCheck = document["secondCheck"] as Boolean,
            missionType = document["missionType"] as String,
        )
    }
}
