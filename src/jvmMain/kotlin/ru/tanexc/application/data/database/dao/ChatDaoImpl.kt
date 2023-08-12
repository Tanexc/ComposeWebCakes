package ru.tanexc.application.data.database.dao

import domain.model.Chat
import domain.model.Message
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import ru.tanexc.application.data.database.entity.ChatTable
import ru.tanexc.application.data.database.entity.ChatTable.asDomain
import ru.tanexc.application.data.database.factory.DatabaseFactory.dbQuery
import ru.tanexc.application.domain.interfaces.ChatDao
import util.exceptions.DataIsNull

class ChatDaoImpl : ChatDao {
    override suspend fun insert(chat: Chat): Chat? = asDomain {
            dbQuery {
                it
                    .insert { row ->

                        row[ChatTable.clientId] = chat.clientId
                        row[ChatTable.title] = chat.title
                        row[ChatTable.creationTimestamp] = chat.creationTimestamp
                        row[ChatTable.newMessagesCount] = 0
                        row[ChatTable.messages] = ""

                    }
                    .resultedValues?.firstOrNull()
            }
        }


    override suspend fun getById(id: Long): Chat? = asDomain {
            dbQuery {
                it
                    .select {
                        ChatTable.id eq id
                    }
                    .firstOrNull()
            }
        }

    override suspend fun getByClientId(clientId: String): Chat? = asDomain {
            dbQuery {
                it
                    .select {
                        ChatTable.clientId eq clientId
                    }
                    .firstOrNull()
            }
        }

    override suspend fun edit(chat: Chat): Unit = dbQuery {
        ChatTable
            .update(
                {
                    ChatTable.id eq chat.id
                }
            ) { row ->

                row[title] = chat.title
                row[creationTimestamp] = chat.creationTimestamp
                row[newMessagesCount] = chat.newMessagesCount
                row[messages] = chat.messages.joinToString(" ")

            }
    }

    override suspend fun getAll(): List<Chat> = dbQuery {
        ChatTable
            .selectAll()
            .map {
                it.asDomain()
            }
    }

    override suspend fun insertMessage(chatId: Long, message: Message): Unit = dbQuery {

        val chat: Chat = asDomain {
            dbQuery {
                it
                    .select {
                        ChatTable.id eq chatId
                    }
                    .firstOrNull()
            }
        }?: throw DataIsNull()

        ChatTable
            .update(
                {ChatTable.id eq chatId}
            ) { row ->
                row[messages] = (chat.messages + message.id).joinToString(" ")
            }
    }


}