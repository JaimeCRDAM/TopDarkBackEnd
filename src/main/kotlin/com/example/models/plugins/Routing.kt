package com.example.models.plugins

import com.example.commands.UserCommands
import com.example.models.objects.Connection
import com.mongodb.client.MongoDatabase
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            val db:MongoDatabase = Connection.getConnection().getDatabase("usuarios") ?: return@get
            call.respondText(db.name)
        }
    }
}
