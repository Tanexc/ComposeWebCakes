package domain.repository

import domain.model.Chat
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import util.State

interface ChatRepository: KoinComponent {
    fun getChatByClientId(clientId: String): Flow<State<Chat>>

    fun getChatById(id: Long): Flow<State<Chat>>

    fun createChat(clientId: String, title: String): Flow<State<Chat>>

}