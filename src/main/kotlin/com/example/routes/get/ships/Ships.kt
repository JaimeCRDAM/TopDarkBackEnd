import com.example.commands.ship_commands.ShipCommands
import com.example.models.Globals
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getShips(shipCommands: ShipCommands) {
    routing {
        route("/auth") {
            authenticate("auth-jwt") {
                get("/ships") {
                    val role = call.principal<com.example.security.UserIdPrincipal>()?.role
                    if (role == Globals.ADMIN_ROLE) {
                        val result = shipCommands.getAllShips()
                        call.respond(result.statusCode, result)
                        return@get
                    }
                    val result = Response<Boolean?>(message ="You are not an admin", statusCode = HttpStatusCode.Unauthorized )
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}