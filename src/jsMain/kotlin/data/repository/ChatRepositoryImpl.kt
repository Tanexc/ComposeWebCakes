package data.repository

import domain.interfaces.api.ChatApi
import domain.model.Chat
import domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.inject
import util.State

class ChatRepositoryImpl: ChatRepository {
    private val chatApi: ChatApi by inject()

    override fun getChatByClientId(clientId: String): Flow<State<Chat>> = flow {
        emit(State.Processing())
        val respond = chatApi.getChatByClientId(clientId)
        when(val chat = respond.data) {
            is Chat -> {
                emit(State.Success(chat))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get chat by client id error"))
            }
        }
    }

    override fun createChat(clientId: String, title: String): Flow<State<Chat>> = flow {
        emit(State.Processing())
        val respond = chatApi.createChat(clientId, title)
        when(val chat = respond.data) {
            is Chat -> {
                emit(State.Success(chat))
            }
            else -> {
                emit(State.Error(message = respond.message?: "create chat error"))
            }
        }
    }

    override fun getAll(value: String): Flow<State<List<Chat>>> = flow {
        emit(State.Processing())
        val respond = chatApi.getAll(value)
        when(val listChat = respond.data) {
            is List<Chat> -> {
                emit(State.Success(listChat))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get all chats error"))
            }
        }
    }
}