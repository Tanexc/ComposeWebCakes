package data.repository

import domain.interfaces.api.MessageApi
import domain.model.Message
import domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.inject
import util.State

class MessageRepositoryImpl: MessageRepository {
    private val messageApi: MessageApi by inject()

    override fun getById(id: Long): Flow<State<Message>> = flow {
        val respond = messageApi.getById(id)
        emit(when(val user = respond.data) {
            is Message -> {
                State.Success(user)
            }

            else -> {
                State.Error(message = respond.message?: "get by id error")
            }
        }
        )
    }

    override fun getByClientId(clientId: String): Flow<State<List<Message>>> = flow {
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