package ru.tanexc.application.presentation.websocket.controller

import domain.model.Chat
import domain.model.Message
import io.ktor.http.*
import io.ktor.server.websocket.*
import io.ktor.util.date.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao
import ru.tanexc.application.domain.interfaces.UserDao
import util.exceptions.DataIsNull
import util.exceptions.Disconnected
import util.exceptions.InvalidData

class ChatConnectionController(
    val data: MutableMap<Long, List<DefaultWebSocketServerSession>> = mutableMapOf(),
) : KoinComponent {
    private val chatDao: ChatDao by inject()
    private val userDao: UserDao by inject()
    private val messageDao: MessageDao by inject()

    suspend fun connection(
        session: DefaultWebSocketServerSession,
        parameters: Parameters
    ) {

        val clientId = parameters["clientId"] ?: throw InvalidData()
        val clientName = parameters["clientName"] ?: throw InvalidData()
        val userId: Long? = parameters["userId"]?.toLong()

        if (userId != null) {
            userDao.getById(userId) ?: throw DataIsNull()
        }
        var chat: Chat = chatDao.getByClientId(clientId) ?: chatDao.insert(
            Chat(
                id = -1L,
                clientId = clientId,
                title = "${clientName}#${clientId.substring(0, 8)}",
                messages = emptyList(),
                creationTimestamp = getTimeMillis(),
                newMessagesCount = 0
            )
        ) ?: throw DataIsNull()

        data[chat.id] = (data[chat.id] ?: emptyList()) + session

        while (true) {
            val data: Message = session.receiveDeserialized()

            val message: Message = messageDao.insert(data) ?: disconnect(session, chat.id, InvalidData())
            chat = chat.copy(messages = chat.messages + message.id)
            chatDao.edit(chat)

            sendMessage(chat.id, message)
        }
    }


    private suspend fun sendMessage(
        chatId: Long,
        message: Message
    ) {
        for (receiver: DefaultWebSocketServerSession in data[chatId] ?: listOf()) {
            receiver.sendSerialized(message)
        }
    }

    private fun disconnect(session: DefaultWebSocketServerSession, chatId: Long, exception: Throwable?): Nothing {
        if (chatId in data.keys) {
            data[chatId] = data[chatId]!!.minus(session)
        }
        throw exception?: Disconnected()
    }

}