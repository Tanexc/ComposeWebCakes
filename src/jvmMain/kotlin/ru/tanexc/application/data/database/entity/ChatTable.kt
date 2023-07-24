package ru.tanexc.application.data.database.entity

import domain.model.Chat
import ru.tanexc.application.domain.model.DatabaseEntity
import domain.model.Domain
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object ChatTable: Table(), DatabaseEntity {

    private val id = long("id").autoIncrement()

    override val primaryKey = PrimaryKey(id)

    private val title = text("title").nullable()
    private val messages = text("messages")
    private val creationTimestamp = long("creationTimestamp")
    private val newMessagesCount = integer("newMessagesCount")

    override fun asDomain(data: ResultRow): Domain = Chat(
        id = data[id],
        title = data[title],
        messages = data[messages].split(" ").map { it.toLong() },
        creationTimestamp = data[creationTimestamp],
        newMessagesCount = data[newMessagesCount]
    )

}