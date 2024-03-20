package itmo.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import itmo.routes.deviceRouting
import itmo.routes.userRouting

fun Application.configureRouting() {
    routing {
        userRouting()
        deviceRouting()
    }
}
