package itmo.db.dao.deviceTypes

import kotlinx.serialization.Serializable

@Serializable
data class DeviceTypeDTO (
    val id : Int,
    val name : String
)