package ru.tanexc.application.data.database.entity

import domain.model.Domain
import domain.model.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import ru.tanexc.application.domain.interfaces.DatabaseEntity

object MessageTable : Table(), DatabaseEntity {

    val id = long("id").autoIncrement()

    override val primaryKey = PrimaryKey(id)

    val text = text("text")
    val replyTo = long("replyTo").nullable()
    val timestamp = long("timestamp")
    val sender = long("sender")

    override suspend fun asDomain(getResult: suspend (Table) -> ResultRow?): Message? {
        val data = getResult(this)?: return null
        return Message(
            id = data[id],
            text = data[text],
            replyTo = data[replyTo],
            timestamp = data[timestamp],
            sender = data[sender]
        )
    }
}