package itmo.db.dao.rooms

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID

@Serializable
data class RoomDTO(
    val id: Int,
    val name: String
)