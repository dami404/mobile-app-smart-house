package itmo.db.dao.deviceTypes

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DeviceTypes : IntIdTable("device_type") {
    private val name = varchar("name", 32)

    fun insert(deviceTypeDTO: DeviceTypeDTO) {
        transaction {
            DeviceTypes.insert { it[name] = deviceTypeDTO.name }
        }
    }

    fun findById(id: Int): DeviceTypeDTO? {
        return transaction {
            try {
                val deviceType = DeviceTypes.select { DeviceTypes.id.eq(id) }.single()
                DeviceTypeDTO(id = deviceType[DeviceTypes.id].value, name = deviceType[name])
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }

    fun findAll(): List<DeviceTypeDTO> {
        return transaction {
            DeviceTypes.selectAll().map {
                DeviceTypeDTO(id = it[DeviceTypes.id].value, name = it[name])
            }
        }
    }
}