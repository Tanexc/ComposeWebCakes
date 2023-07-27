package ru.tanexc.application.data.database.dao

import domain.model.Message
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.tanexc.application.data.database.entity.MessageTable
import ru.tanexc.application.data.database.factory.DatabaseFactory.dbQuery
import ru.tanexc.application.domain.interfaces.MessageDao

class MessageDaoImpl : MessageDao {
    override suspend fun insert(message: Message): Message? = MessageTable
        .asDomain { table ->
            dbQuery {
                table
                    .insert { row ->

                        row[MessageTable.text] = message.text
                        row[MessageTable.sender] = message.sender
                        row[MessageTable.replyTo] = message.replyTo
                        row[MessageTable.timestamp] = message.timestamp

                    }
                    .resultedValues?.firstOrNull()
            }
        }

    override suspend fun getById(id: Long): Message? = MessageTable
        .asDomain { table ->
            dbQuery {
                table
                    .select {
                        MessageTable.id eq id
                    }
                    .firstOrNull()
            }
        }

    override suspend fun getByIdList(listId: List<Long>): List<Message> = dbQuery {
        MessageTable
            .select {
                MessageTable.id inList listId
            }
            .map { row ->
                Message(
                    id = row[MessageTable.id],
                    replyTo = row[MessageTable.replyTo],
                    sender = row[MessageTable.sender],
                    text = row[MessageTable.text],
                    timestamp = row[MessageTable.timestamp]
                )
            }
    }
}
