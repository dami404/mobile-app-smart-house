package itmo.db.dao.notifications

import itmo.db.dao.devices.Devices
import itmo.db.dao.users.Users
import kotlinx.datetime.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.ZoneOffset


object Notifications : IntIdTable() {
    val deviceId = reference("device_id", Devices, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val time = timestamp("time")
    val text = varchar("text", 256)

    fun insert(notificationDTO: NotificationDTO) {
        transaction {
            Notifications.insert {
                it[deviceId] = notificationDTO.deviceId
                it[userId] = notificationDTO.userId
                it[time] = notificationDTO.time.toJavaLocalDateTime().toInstant(ZoneOffset.UTC)
                it[text] = notificationDTO.text
            }
        }
    }

    fun findById(id: Int): NotificationDTO? {
        return transaction {
            try {
                val notification = Notifications.select { Notifications.id.eq(id) }.single()
                NotificationDTO(
                    id = notification[Notifications.id].value,
                    deviceId = notification[Notifications.deviceId].value,
                    userId = notification[Notifications.userId].value,
                    text = notification[Notifications.text],
                    time = notification[Notifications.time].toKotlinInstant().toLocalDateTime(TimeZone.UTC)
                )
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }
}