package itmo.db.dao.notifications

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDTO(
        val id: Int,
        val deviceId: Int,
        val userId: Int,
        val time: LocalDateTime,
        val text: String
)