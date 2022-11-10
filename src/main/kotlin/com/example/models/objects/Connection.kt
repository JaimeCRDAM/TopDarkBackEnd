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

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO){
        block()
    }

    fun getTable(database:String, name:String): MongoCollection<Document> {
        return dbConnection.getDatabase("usuarios").getCollection(name)
    }

    fun insertIntoTable(database:String, name:String, document:Document): InsertOneResult {
        return dbConnection.getDatabase(database).getCollection(name).insertOne(document)
    }

    fun getCurrentRows(database:String, name:String): Int{
        return dbConnection.getDatabase(database).getCollection(name).listIndexes().toList().size
    }

    fun getDocumentById(database:String, name: String, id: ObjectId?): Document? {
        return dbConnection.getDatabase(database).getCollection(name).find(Document("_id", id)).first()
    }

    fun getDocumentByUserName(username:String): Document?{
        return dbConnection.getDatabase("usuarios").getCollection("users").find(Document("userName",username)).first()
    }

    fun getConnection(): MongoClient{
        return dbConnection
    }

    fun dropTable(){
        dbConnection.getDatabase("usuarios").getCollection("users").drop()
    }
}
