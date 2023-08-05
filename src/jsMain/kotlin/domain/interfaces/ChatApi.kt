package domain.interfaces

import domain.model.Chat
import domain.model.RespondData
import org.koin.core.component.KoinComponent

interface ChatApi: KoinComponent {
    suspend fun getChatByClientId(clientId: String): RespondData<Chat>

    suspend fun getChatById(id: Long): RespondData<Chat>

    suspend fun createChat(clientId: String, title: String): RespondData<Chat>
}