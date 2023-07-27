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
import util.exceptions.DataIsNull

class ChatRepositoryImpl: ChatRepository, KoinComponent {
    private val chatDao: ChatDao by inject()
    private val messageDao: MessageDao by inject()

    override fun getChatByClientId(clientId: String): Flow<State<Chat?>> = flow {
        try {
            emit(State.Processing())
            val chat = chatDao.getByClientId(clientId)?: throw DataIsNull()
            emit(State.Success(chat))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "getting chat by userId = $clientId problem"))
        }
    }

    override fun createChat(clientId: String): Flow<State<Chat>> = flow {
        try {
            emit(State.Processing())

            val chat: Chat = chatDao.insert(
                Chat(
                    id = -1L,
                    clientId = clientId,
                    title = "#${clientId.substring(0, 8)}",
                    messages = emptyList(),
                    creationTimestamp = getTimeMillis(),
                    newMessagesCount = 0
                )
            )?: throw DataIsNull()

            emit(State.Success(chat))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "creating chat problem"))
        }
    }

    override fun insertMessage(chatData: Chat, data: Message): Flow<State<Chat>> = flow {
        try {
            emit(State.Processing())
            val message: Message = messageDao.insert(data)?: throw DataIsNull()

            val chat: Chat = chatData.copy(messages = chatData.messages + message.id)
            chatDao.edit(chat)
            emit(State.Success(chat))

        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "insert message problem"))
        }
    }
}