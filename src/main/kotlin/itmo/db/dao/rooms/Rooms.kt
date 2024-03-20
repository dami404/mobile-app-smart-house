package itmo.db.dao.rooms

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Rooms : IntIdTable("room") {
    private val name = varchar("name", 32)

    fun insert(roomDTO: RoomDTO) {
        transaction {
            Rooms.insert { it[name] = roomDTO.name }
        }
    }

    fun findById(id: Int): RoomDTO? {
        return transaction {
            try {
                val room = Rooms.select { Rooms.id.eq(id) }.single()
                RoomDTO(id = room[Rooms.id].value, name = room[name])
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }

    fun findAll(): List<RoomDTO> {
        return transaction {
            Rooms.selectAll().map {
                RoomDTO(id = it[Rooms.id].value, name = it[name])
            }
        }
    }
}