package ru.tanexc.application.domain.use_cases.message_use_cases

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.repository.MessageRepository
import util.State

class MessageGetByIdUseCase: KoinComponent {
    private val repository: MessageRepository by inject()

    suspend operator fun invoke(id: Long): Flow<State<Message?>> = repository.getById(id)
}