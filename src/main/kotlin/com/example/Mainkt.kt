package com.example

import com.example.commands.UserCommandsImpl
import com.example.models.objects.Connection
import com.example.models.plugins.jwtAuth
import com.example.models.plugins.registerRoute
import com.example.routes.authroutes.login
import com.example.security.configureSecurity
import com.example.security.hash
import com.example.services.CreateUserParams
import com.example.services.UserServiceImpl
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.runBlocking

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
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
        }
    }
    val userService = UserServiceImpl()
    val userCommands = UserCommandsImpl(userService)
    registerRoute(userCommands)
    configureSecurity(userCommands, userService)
    login()
    jwtAuth()

}

fun main(args: Array<String>) {
    MainClass()
}

