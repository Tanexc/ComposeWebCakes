package domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import util.State

interface MessageRepository: KoinComponent {
    fun getById(id: Long): Flow<State<Message>>

    fun getByClientId(clientId: String): Flow<State<List<Message>>>
}