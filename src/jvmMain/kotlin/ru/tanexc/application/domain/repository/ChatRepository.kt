package ru.tanexc.application.domain.repository

import domain.model.Chat
import domain.model.Message
import kotlinx.coroutines.flow.Flow
import util.State

interface ChatRepository {

    fun getChatByClientId(clientId: String): Flow<State<Chat?>>

    fun createChat(clientId: String): Flow<State<Chat>>

    fun insertMessage(chatData: Chat, data: Message): Flow<State<Chat>>
}