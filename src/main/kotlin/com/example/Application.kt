package com.example

import com.example.commands.mission_commands.MissionCommands
import com.example.commands.mission_commands.MissionCommandsImpl
import com.example.commands.pilots_missions_ships.PilotsMissionsShipsImpl
import com.example.commands.ship_commands.ShipCommandsImpl
import com.example.commands.user_commands.UserCommandsImpl
import com.example.models.plugins.jwtAuth
import com.example.models.plugins.registerPilot
import com.example.models.plugins.registerShip
import com.example.routes.authroutes.login
import com.example.routes.get.all_data.getAllData
import com.example.routes.get.missions.getMissions
import com.example.routes.register.mission.registerMission
import com.example.routes.register.pilot_to_mission.registerPilotToMission
import com.example.security.configureSecurity
import com.example.services.missionservices.MissionServiceImpl
import com.example.services.pilots_missions_ships.PilotsMissionsShipsServiceImpl
import com.example.services.ship_services.ShipServiceImpl
import com.example.services.user_services.UserServiceImpl
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import getPilots
import getShips
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
    val userCommands = UserCommandsImpl(userService)
    val missionService = MissionServiceImpl()
    val missionCommands = MissionCommandsImpl(missionService)
    val shipService = ShipServiceImpl()
    val shipCommands = ShipCommandsImpl(shipService)
    val pilotsMissionsShipsService = PilotsMissionsShipsServiceImpl()
    val pilotsMissionsShipsImpl = PilotsMissionsShipsImpl(pilotsMissionsShipsService)
    registerPilot(userCommands)
    getPilots(userCommands)
    registerMission(missionCommands)
    getMissions(missionCommands)
    registerPilotToMission(pilotsMissionsShipsImpl)
    getAllData(pilotsMissionsShipsImpl)
    registerShip(shipCommands)
    getShips(shipCommands)
    configureSecurity(userCommands, userService)
    login()
    jwtAuth()


}

/*fun main(args: Array<String>) {
    MainClass()
}*/
fun main(args: Array<String>): Unit = EngineMain.main(args)

