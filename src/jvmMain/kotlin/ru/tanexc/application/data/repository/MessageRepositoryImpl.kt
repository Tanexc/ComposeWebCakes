package ru.tanexc.application.data.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.interfaces.MessageDao
import ru.tanexc.application.domain.repository.MessageRepository
import util.State
import util.exceptions.DataIsNull

class MessageRepositoryImpl: MessageRepository, KoinComponent {
    private val messageDao: MessageDao by inject()

    override suspend fun create(data: Message): Flow<State<Message>> = flow {
        try {
            emit(State.Processing())

            val message: Message = messageDao.insert(data)?: throw DataIsNull()

            emit(State.Success(message))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "creating message problem"))
        }

    }

    override suspend fun getById(id: Long): Flow<State<Message?>> = flow {
        try {
            emit(State.Processing())
            val message: Message = messageDao.getById(id)?: throw DataIsNull()
            emit(State.Success(message))
        } catch (e: Exception) {
            emit(State.Error(message = e.message?: "getting message by id = $id problem"))
        }

    }
}