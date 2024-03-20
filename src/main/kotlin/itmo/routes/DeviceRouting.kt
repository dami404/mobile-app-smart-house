package itmo.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import itmo.db.dao.devices.DeviceDTO
import itmo.db.dao.devices.Devices


fun Route.deviceRouting() {
    route("/devices") {
        get("/") {
            if (call.request.queryParameters.isEmpty()) {
                call.respond(Devices.findAll())
            }

            val roomId = call.request.queryParameters["roomId"]?.toIntOrNull()
            val userId = call.request.queryParameters["userId"]?.toIntOrNull()

            if (userId != null && userId > 0) {
                val devices = if (roomId != null && roomId > 0) {
                    Devices.findAllByUserAndRoom(userId, roomId)
                } else {
                    Devices.findAllByUser(userId)
                }
                call.respond(devices)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Необходимо заполнить все поля")
            }
        }
        get("/{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val device: DeviceDTO? = Devices.findById(id)
                if (device == null)
                    call.respondText("Прибора не существует")
                else
                    call.respond(device)
            }
        }
        post("/") {
            try {
                val body = call.receive<DeviceDTO>()
                if (body.name.isNotEmpty() && body.roomId > 0 && body.typeId > 0 && body.userId > 0) {
                    call.respond(body)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Необходимо заполнить все поля1")
                }
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, "Необходимо заполнить все поля")
            }
        }
    }
}