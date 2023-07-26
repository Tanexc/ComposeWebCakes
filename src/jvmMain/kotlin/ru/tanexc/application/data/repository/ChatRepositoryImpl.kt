package ru.tanexc.application.data.repository

import domain.model.Chat
import domain.model.Message
import ru.tanexc.application.domain.repository.ChatRepository
import io.ktor.util.date.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao
import util.State

class ChatRepositoryImpl: ChatRepository, KoinComponent {
    private val chatDao: ChatDao by inject()
    private val messageDao: MessageDao by inject()

    override fun getChatByUserId(userId: String): Flow<State<Chat?>> = flow {
        emit(State.Processing())
        val chat = chatDao.getByUserId(userId)
        if (chat == null) {
            emit(State.Error())
        } else {
            emit(State.Success(chat))
        }
    }

    override fun createChat(userId: String): Flow<State<Chat>> = flow {
        emit(State.Processing())
        val chat: Chat? = chatDao.insert(
            Chat(
                id = -1L,
                userId = userId,
                title = "#${userId.substring(0, 8)}",
                messages = emptyList(),
                creationTimestamp = getTimeMillis(),
                newMessagesCount = 0
            )
        )
        if (chat == null) {
            emit(State.Error())
        } else {
            emit(State.Success(chat))
        }
    }

    override fun insertMessage(chatData: Chat, data: Message): Flow<State<Chat>> = flow {
        emit(State.Processing())
        val message: Message? = messageDao.insert(data)

        if (message == null) {
            emit(State.Error())
        } else {
            val chat: Chat = chatData.copy(messages = chatData.messages + data.id)
            chatDao.edit(chat)
            emit(State.Success(chat))
        }

    }
}