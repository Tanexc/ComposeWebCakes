package ru.tanexc.application.data.database.entity

import domain.model.Domain
import domain.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import ru.tanexc.application.domain.interfaces.DatabaseEntity

object UserTable : Table(), DatabaseEntity {

    private val id = long("id").autoIncrement()

    override val primaryKey = PrimaryKey(id)

    private val name = text("title")
    private val surname = text("surname")
    private val chatIds = text("chatIds")
    private val creationTimestamp = long("creationTimestamp")
    private val password = binary("password", length = 256)
    private val token = varchar("token",512)

    override fun asDomain(data: ResultRow): Domain = User(
        id = data[id],
        name = data[name],
        surname = data[surname],
        chatIds = data[chatIds].split(" ").map { it.toLong() },
        creationTimestamp = data[creationTimestamp],
        password = data[password],
        token = data[token]
    )
}