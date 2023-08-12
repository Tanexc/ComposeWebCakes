package ru.tanexc.application.domain.interfaces

import domain.model.Chat
import domain.model.Message

interface ChatDao {
    suspend fun insert(chat: Chat): Chat?

    suspend fun getById(id: Long): Chat?

    suspend fun getByClientId(clientId: String): Chat?

    suspend fun edit(chat: Chat)

    suspend fun getAll(): List<Chat>

    suspend fun insertMessage(chatId: Long, message: Message)
}