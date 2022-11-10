package com.example

import com.example.commands.UserCommandsImpl
import com.example.models.objects.Connection
import com.example.models.plugins.authRoutes
import com.example.models.plugins.configureRouting
import com.example.security.configureSecurity
import com.example.security.hash
import com.example.services.CreateUserParams
import com.example.services.UserServiceImpl
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import io.ktor.serialization.kotlinx.json.*

class MainClass {
    init {
        var connection = Connection.getConnection()
        println(hash("12354"))
        runBlocking {
            UserServiceImpl().registerUser(CreateUserParams("no", "no", "no", "no"))
        }
        embeddedServer(Netty, port = 9003, host = "0.0.0.0", module = Application::module).start(wait = true)
    }
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
    val userService = UserServiceImpl()
    val userCommands = UserCommandsImpl(userService)
    authRoutes(userCommands)
    configureSecurity()
}

fun main(args: Array<String>) {
    MainClass()
}

