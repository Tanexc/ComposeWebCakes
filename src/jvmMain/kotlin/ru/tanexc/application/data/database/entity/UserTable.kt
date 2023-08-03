package ru.tanexc.application.data.database.entity

import domain.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import ru.tanexc.application.domain.interfaces.DatabaseEntity

object UserTable : Table(), DatabaseEntity {

    val id = long("id").autoIncrement()

    override val primaryKey = PrimaryKey(id)

    val name = text("title")
    val surname = text("surname")
    val login = text("login").uniqueIndex()

    val chatIds = text("chatIds")
    val creationTimestamp = long("creationTimestamp")
    val password = binary("password", length = 256)
    val token = varchar("token",512)

    override suspend fun asDomain(getResult: suspend (Table) -> ResultRow?): User? {
        val data = getResult(this)?: return null

        return User(
            id = data[id],
            chatIds = data[chatIds].split(" ").map { it.toLong() },
            creationTimestamp = data[creationTimestamp],
            name = data[name],
            password = data[password],
            surname = data[surname],
            token = data[token],
            login = data[login]
        )
    }
}