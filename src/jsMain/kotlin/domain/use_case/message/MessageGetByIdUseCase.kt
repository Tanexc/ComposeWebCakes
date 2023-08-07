package domain.use_case.message

import domain.model.Message
import domain.repository.MessageRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class MessageGetByIdUseCase: KoinComponent {
    private val repository: MessageRepository by inject()

    suspend operator fun invoke(id: Long): State<Message> = repository.getById(id)

}