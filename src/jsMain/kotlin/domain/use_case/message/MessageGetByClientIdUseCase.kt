package domain.use_case.message

import domain.model.Message
import domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class MessageGetByClientIdUseCase: KoinComponent {
    private val repository: MessageRepository by inject()

    operator fun invoke(clientId: String): Flow<State<List<Message>>> = repository.getByClientId(clientId)

}