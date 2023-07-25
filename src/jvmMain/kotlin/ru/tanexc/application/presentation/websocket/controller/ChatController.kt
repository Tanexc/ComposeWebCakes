package ru.tanexc.application.presentation.websocket.controller

import domain.model.Chat
import domain.model.Message
import io.ktor.http.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import domain.repository.ChatRepository
import util.State


class ChatConnectionController(
    val data: MutableMap<Long, List<DefaultWebSocketServerSession>> = mutableMapOf(),
): KoinComponent {
    private val chatRepository: ChatRepository by inject()

    fun connect(
        session: DefaultWebSocketServerSession,
        parameters: Parameters
    ): Flow<State<Chat?>> = flow {

        val userId = parameters["userId"]
        val userName = parameters["userName"]

        if (userId == null) {
            emit(State.Error())
        }
        else if (userName == null) {
            emit(State.Error())
        }
        else {
            TODO()
        }
    }


    suspend fun sendMessage(
        userId: Long,
        message: Message
    ) {

        TODO("Inserting message to chat")

        for (receiver: DefaultWebSocketServerSession in data[userId] ?: listOf()) {
            receiver.sendSerialized(message)
        }

    }

    fun disconnect(session: DefaultWebSocketServerSession, id: Long) {
        if (id in data.keys) {
            data[id] = data[id]!!.minus(session)
        }
    }

}