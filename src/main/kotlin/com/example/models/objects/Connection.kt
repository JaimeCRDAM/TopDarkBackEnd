package com.example.models.objects

import com.example.models.Globals
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId

object Connection {

    private val dbConnection: MongoClient = Factories.dbInit()

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        block()
    }

    fun insertIntoTable(database: String = "usuarios", table: String, document: Document): InsertOneResult {
        return dbConnection.getDatabase(database).getCollection(table).insertOne(document)
    }

    fun getDocumentById(database: String = "usuarios", table: String = "users", id: ObjectId?): Document? {
        val id = if(table == "roles") id.toString() else id
        val result = dbConnection.getDatabase(database)
        val result2 = result.getCollection(table)
        val result3 = result2.find(Document("_id", id))
        return result3.first()
    }

    fun getUserByUserName(database: String = "usuarios", table: String = "users", username: String): Document? {
        return dbConnection.getDatabase(database).getCollection(table).find(Document("username", username)).first()
    }

    fun getShipByName(name: String): Document? {
        return dbConnection.getDatabase("usuarios").getCollection("ships").find(Document("plate", name)).first()
    }

    fun getRoleByUserName(database: String = "usuarios", table: String = "users", userName: String): String {
        val role = getUserByUserName(username = userName)?.get("role").toString()
        return role
    }

    fun getAllPilots(database: String = "usuarios", table: String = "users"): List<Document> {
        return dbConnection.getDatabase(database).getCollection(table).find().filter { it["role"] == Globals.USER_ROLE }
    }
    fun getPilotById(database: String = "usuarios", table: String = "users", id: String): Document? {
        return dbConnection.getDatabase(database).getCollection(table).find().filter { it["_id"].toString() == id }.first()
    }

    fun getAllMission(database: String = "usuarios", table: String = "missions"): List<Document>?{
        return dbConnection.getDatabase(database).getCollection(table).find().filter{ it == it }
    }
    fun getMissionById(database: String = "usuarios", table: String = "missions", id: String): Document?{
        return dbConnection.getDatabase(database).getCollection(table).find().filter{ it["_id"].toString() == id }.first()
    }

    fun getAllShips(database: String = "usuarios", table: String = "ships"): List<Document>?{
        return dbConnection.getDatabase(database).getCollection(table).find().filter{ it == it }
    }

    fun getCollection(name: String): MongoCollection<Document> {
        return dbConnection.getDatabase("usuarios").getCollection(name)
    }
}
