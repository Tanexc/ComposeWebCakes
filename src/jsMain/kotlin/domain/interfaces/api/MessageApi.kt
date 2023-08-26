package domain.interfaces.api

import domain.model.Message
import domain.model.RespondData
import org.koin.core.component.KoinComponent

interface MessageApi: KoinComponent {

    suspend fun getById(id: Long): RespondData<Message>

    suspend fun getByClientId(clientId: String): RespondData<List<Message>>
}