package domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import util.State

interface MessageRepository: KoinComponent {
    suspend fun getById(id: Long): State<Message>

    fun getByClientId(clientId: String): Flow<State<List<Message>>>
}