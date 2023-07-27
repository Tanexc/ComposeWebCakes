package ru.tanexc.application.domain.use_cases.chat_use_cases

import domain.model.Chat
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.repository.ChatRepository
import util.State

class GetChatByClientIdUseCase: KoinComponent {
    private val repository: ChatRepository by inject()

    operator fun invoke(clientId: String): Flow<State<Chat?>> = repository.getChatByClientId(clientId)
}