package ru.tanexc.application.data.repository

import domain.model.Chat
import domain.model.Message
import domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao
import util.State

class ChatRepositoryImpl: ChatRepository, KoinComponent {
    private val chatDao: ChatDao by inject()
    private val messageDao: MessageDao by inject()

    override fun getChatById(userId: String): Flow<State<Chat>> {
        TODO("Not yet implemented")
    }

    override fun createChat(userId: String): Flow<State<Chat>> {
        TODO("Not yet implemented")
    }

    override fun insertMessage(message: Message): Flow<State<Chat>> {
        TODO("Not yet implemented")
    }
}