package itmo

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import itmo.plugins.*
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPubSub

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/mobile",

        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "sobuka2100"
    )


    configureRouting()
    configureSerialization()
}


