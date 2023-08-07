package domain.use_case.chat

import domain.model.Chat
import domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class ChatCreateUseCase: KoinComponent {
    private val repository: ChatRepository by inject()

    operator fun invoke(clientId: String, title: String): Flow<State<Chat>> = repository.createChat(clientId, title = title)

}