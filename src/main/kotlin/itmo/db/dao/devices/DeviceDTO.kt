package itmo.db.dao.devices

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDTO (
    val id : Int?,
    val name: String,
    val typeId : Int,
    val roomId : Int,
    val userId : Int
)