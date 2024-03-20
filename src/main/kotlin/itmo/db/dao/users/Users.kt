package itmo.db.dao.users

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : IntIdTable() {
    private val login = varchar("login", 25)
    private val password = varchar("password", 25)
    private val email = varchar("email", 25)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email
            }
        }
    }

    fun findByLogin(login: String): UserDTO? {
        return transaction {
            try {
                val user = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    id = user[Users.id].value,
                    login = user[Users.login],
                    password = user[password],
                    email = user[email]
                )
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }

    fun findById(id: Int): UserDTO? {
        return transaction {
            try {
                val user = Users.select { Users.id.eq(id) }.single()
                UserDTO(
                    id = user[Users.id].value,
                    login = user[Users.login],
                    password = user[password],
                    email = user[email]
                )
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }

    fun findAll(): List<UserDTO> {
        return transaction {
            Users.selectAll().map {
                UserDTO(
                    id = it[Users.id].value,
                    login = it[login],
                    password = it[password],
                    email = it[email]
                )
            }
        }
    }
}