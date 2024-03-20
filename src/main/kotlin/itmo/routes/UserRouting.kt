package itmo.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import itmo.db.dao.users.UserDTO
import itmo.db.dao.users.Users

fun Route.userRouting() {
    route("/users") {
        get("/") {
            call.respond(Users.findAll())
        }
        get("/{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val user: UserDTO? = Users.findById(id)
                if (user == null)
                    call.respondText("Пользователя не существует")
                else
                    call.respond(user)
            }
        }
        post("/") {
            try {
                val body = call.receive<UserDTO>()
                if (body.login.isNotEmpty() && body.password.isNotEmpty() && body.email.isNotEmpty()) {
                    call.respond(body)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Необходимо заполнить все поля")
                }
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, "Необходимо заполнить все поля")
            }
        }
    }
}