package ru.tanexc.application.domain.repository

import domain.model.Chat
import domain.model.Message
import kotlinx.coroutines.flow.Flow
import util.State

interface ChatRepository {

    fun getChatByClientId(clientId: String): Flow<State<Chat?>>

    fun getChatById(id: Long): Flow<State<Chat?>>

    fun createChat(clientId: String, title: String?): Flow<State<Chat>>

    fun insertMessage(chatData: Chat, data: Message): Flow<State<Chat>>

    fun getAllChats(token: String): Flow<State<List<Chat>>>
}