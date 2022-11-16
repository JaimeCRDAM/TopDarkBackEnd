package com.example.services.missionservices


import com.example.models.Globals
import com.example.models.User
import com.example.models.objects.Connection
import com.example.models.objects.Connection.dbQuery
import com.example.security.hash
import com.example.utils.Response
import com.mongodb.client.result.InsertOneResult
import io.ktor.http.*
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class MissionServiceImpl : MissionService {
    override suspend fun registerMission(params: RegisterMissionParams, missionType: String): Response<Boolean?> {
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

            statement = Connection.insertIntoTable("usuarios", "missions", userDocument)
            insertedId = statement.insertedId!!.asObjectId().value
        }
        val didItEnter  = Connection.getDocumentById(table = "missions", id = insertedId)
        if (didItEnter != null){
            return Response(message = "Mission registered", statusCode = HttpStatusCode.OK)
        }
        return Response(message = "Mission could not be registered", statusCode = HttpStatusCode.NotAcceptable)
    }
}