package data.repository

import domain.interfaces.MessageApi
import domain.model.Message
import domain.model.User
import domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class MessageRepositoryImpl: MessageRepository {
    private val messageApi: MessageApi by inject()

    override suspend fun getById(id: Long): Flow<State<Message>> = flow {
        emit(State.Processing())
        val respond = messageApi.getById(id)
        when(val user = respond.data) {
            is Message -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get by id error"))
            }
        }
    }

    override suspend fun getByClientId(clientId: String): Flow<State<List<Message>>> = flow {
        emit(State.Processing())
        val respond = messageApi.getByClientId(clientId)
        when(val user = respond.data) {
            is List<Message> -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get by client id error"))
            }
        }
    }

}