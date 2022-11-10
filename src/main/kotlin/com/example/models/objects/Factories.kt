package com.example.models.objects

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients

object Factories {

    fun dbInit(uri: String = ""): MongoClient {
        val uriVar: String = if (uri == "") "mongodb://mongoadmin:mongoadmin@192.168.1.132:27017/?maxPoolSize=20&w=majority" else uri
        return MongoClients.create(uriVar)
    }
}