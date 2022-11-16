package com.example

import com.example.commands.UserCommandsImpl
import com.example.models.plugins.jwtAuth
import com.example.models.plugins.registerRoute
import com.example.routes.authroutes.login
import com.example.routes.register.mission.registerMission
import com.example.security.configureSecurity
import com.example.services.missionservices.MissionServiceImpl
import com.example.services.userservices.UserServiceImpl
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

class MainClass {
    init {
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
    val missionService = MissionServiceImpl()
    val userCommands = UserCommandsImpl(userService)
    registerRoute(userCommands)
    registerMission(missionService)
    configureSecurity(userCommands, userService)
    login()
    jwtAuth()


}

/*fun main(args: Array<String>) {
    MainClass()
}*/
fun main(args: Array<String>): Unit = EngineMain.main(args)

