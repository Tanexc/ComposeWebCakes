package ru.tanexc.application.data.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.MessageDao
import ru.tanexc.application.domain.repository.MessageRepository
import util.State

class MessageRepositoryImpl: MessageRepository, KoinComponent {
    private val messageDao: MessageDao by inject()

    override suspend fun create(data: Message): Flow<State<Message>> = flow {
        emit(State.Processing())
        val message: Message? = messageDao.insert(data)
        if (message == null) {
            emit(State.Error())
        } else {
            emit(State.Success(message))
        }
    }

    override suspend fun getById(id: Long): Flow<State<Message?>> = flow {
        emit(State.Processing())
        val message: Message? = messageDao.getById(id)
        if (message == null) {
            emit(State.Error())
        } else {
            emit(State.Success(message))
        }
    }
}