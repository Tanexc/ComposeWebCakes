package ru.tanexc.application.domain.use_cases.message_use_cases

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.repository.MessageRepository
import util.State

class CreateMessageUseCase: KoinComponent {
    private val repository: MessageRepository by inject()

    suspend operator fun invoke(message: Message): Flow<State<Message>> = repository.create(message)
}