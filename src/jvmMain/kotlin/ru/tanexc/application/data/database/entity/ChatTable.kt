package ru.tanexc.application.data.database.entity

import domain.model.Chat
import ru.tanexc.application.domain.interfaces.DatabaseEntity
import domain.model.Domain
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object ChatTable: Table(), DatabaseEntity {

    val id = long("id").autoIncrement()

    override val primaryKey = PrimaryKey(id)

    val title = text("title").nullable()
    val messages = text("messages")
    val creationTimestamp = long("creationTimestamp")
    val newMessagesCount = integer("newMessagesCount")

    override suspend fun asDomain(
        getResult: suspend (Table) -> ResultRow?
    ): Chat? {
        val data = getResult(this)?: return null

        return Chat(
            id = data[id],
            messages = data[messages].split(" ").map { it.toLong() },
            creationTimestamp = data[creationTimestamp],
            newMessagesCount = data[newMessagesCount],
            title = data[title]
        )

    }
}