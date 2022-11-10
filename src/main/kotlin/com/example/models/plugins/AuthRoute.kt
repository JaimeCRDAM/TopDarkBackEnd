package com.example.models.plugins

import com.example.commands.UserCommands
import com.example.models.User
import com.example.models.objects.Connection.dropTable
import com.example.services.CreateUserParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.modules.SerializersModule

fun Application.authRoutes(userCommands: UserCommands) {
    routing {
        route("/auth"){
            post("/register"){
                //dropTable()
                val params = Json.decodeFromString<CreateUserParams>(call.receive())
                val result = userCommands.registerUser(params)
                call.respond(result.statusCode, Json.encodeToJsonElement(result))
            }
        }
    }
}