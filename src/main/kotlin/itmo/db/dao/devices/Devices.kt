package itmo.db.dao.devices


import itmo.db.dao.deviceTypes.DeviceTypes
import itmo.db.dao.rooms.Rooms
import itmo.db.dao.users.Users
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Devices : IntIdTable("device") {
    val typeId = reference("type_id", DeviceTypes)
    val name = varchar("name", 32)
    val roomId = reference("room_id", Rooms)
    val userId = reference("user_id", Users)

    fun insert(deviceDTO: DeviceDTO) {
        transaction {
            Devices.insert {
                it[typeId] = deviceDTO.typeId
                it[name] = deviceDTO.name
                it[roomId] = deviceDTO.roomId
                it[userId] = deviceDTO.userId
            }
        }
    }

    fun findById(id: Int): DeviceDTO? {
        return transaction {
            try {
                val device = Devices.select { Devices.id.eq(id) }.single()
                DeviceDTO(
                    id = device[Devices.id].value,
                    name = device[name],
                    typeId = device[typeId].value,
                    roomId = device[roomId].value,
                    userId = device[userId].value
                )
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }

    fun findAll(): List<DeviceDTO> {
        return transaction {
            Devices.selectAll().map {
                DeviceDTO(
                    id = it[Devices.id].value,
                    name = it[name],
                    typeId = it[typeId].value,
                    roomId = it[roomId].value,
                    userId = it[userId].value
                )
            }
        }
    }

    fun findAllByUser(userID: Int): List<DeviceDTO> {
        return transaction {
            val devices = Devices.select { userId.eq(userID) }
            devices.map {
                DeviceDTO(
                    id = it[Devices.id].value,
                    name = it[name],
                    typeId = it[typeId].value,
                    roomId = it[roomId].value,
                    userId = it[userId].value
                )
            }
        }
    }

    fun findAllByUserAndRoom(userID: Int, roomID: Int): List<DeviceDTO> {
        return transaction {
            val devices = Devices.select {
                userId.eq(userID)
                roomId.eq(roomID)
            }
            devices.map {
                DeviceDTO(
                    id = it[Devices.id].value,
                    name = it[name],
                    typeId = it[typeId].value,
                    roomId = it[roomId].value,
                    userId = it[userId].value
                )
            }
        }
    }

}