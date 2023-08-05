package domain.interfaces

import domain.model.Message
import domain.model.RespondData
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import util.State

interface MessageApi: KoinComponent {

    suspend fun getById(id: Long): RespondData<Message>

    suspend fun getByClientId(clientId: String): RespondData<List<Message>>
}