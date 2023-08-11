package ru.tanexc.application.data.repository

import domain.model.Chat
import domain.model.Message
import domain.model.User
import io.ktor.util.date.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao
import ru.tanexc.application.domain.interfaces.UserDao
import ru.tanexc.application.domain.repository.ChatRepository
import util.State
import util.exceptions.DataIsNull

class ChatRepositoryImpl: ChatRepository, KoinComponent {
    private val chatDao: ChatDao by inject()
    private val messageDao: MessageDao by inject()
    private val userDao: UserDao by inject()

    override fun getChatByClientId(clientId: String): Flow<State<Chat?>> = flow {
        try {
            emit(State.Processing())
            val chat = chatDao.getByClientId(clientId)
            emit(State.Success(chat))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "getting chat by userId = $clientId problem"))
        }
    }

    override fun getChatById(id: Long): Flow<State<Chat?>> = flow {
        try {
            emit(State.Processing())
            val chat = chatDao.getById(id)
            emit(State.Success(chat))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "getting chat with id = $id problem"))
        }
    }

    override fun createChat(clientId: String, title: String?): Flow<State<Chat>> = flow {
        try {
            emit(State.Processing())

            val chat: Chat = chatDao.insert(
                Chat(
                    id = -1L,
                    clientId = clientId,
                    title = "${title?:""}#${clientId.substring(0, 8)}",
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

    override fun getAllChats(token: String): Flow<State<List<Chat>>> = flow {
        try {
            emit(State.Processing())
            val user: User = userDao.getByToken(token) ?: throw DataIsNull()

            val chats: List<Chat> = chatDao.getAll()

            emit(State.Success(chats))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "get all chats error"))
        }
    }
}