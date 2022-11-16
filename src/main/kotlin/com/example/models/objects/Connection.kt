package com.example.models.objects

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
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

    fun getTable(database: String = "usuarios", table: String): MongoCollection<Document> {
        return dbConnection.getDatabase(database).getCollection(table)
    }

    fun insertIntoTable(database: String = "usuarios", table: String, document: Document): InsertOneResult {
        return dbConnection.getDatabase(database).getCollection(table).insertOne(document)
    }

    fun getCurrentRows(database: String = "usuarios", table: String): Int {
        return dbConnection.getDatabase(database).getCollection(table).listIndexes().toList().size
    }

    fun getDocumentById(database: String = "usuarios", table: String = "users", id: ObjectId?): Document? {
        val id = if(table == "roles") id.toString() else id
        val result = dbConnection.getDatabase(database)
        val result2 = result.getCollection(table)
        val result3 = result2.find(Document("_id", id))
        return result3.first()
    }

    fun getDocumentByUserName(database: String = "usuarios", table: String = "users", username: String): Document? {
        return dbConnection.getDatabase(database).getCollection(table).find(Document("username", username)).first()
    }

    fun getRoleByUserName(database: String = "usuarios", table: String = "roles", userName: String): String {
        val id = getDocumentByUserName(username = userName)?.get("_id") as ObjectId
        val role = getDocumentById(database, table, id)?.get("role").toString()
        return role
    }

    fun getConnection(): MongoClient {
        return dbConnection
    }

    fun dropTable() {
        dbConnection.getDatabase("usuarios").getCollection("users").drop()
    }
}
