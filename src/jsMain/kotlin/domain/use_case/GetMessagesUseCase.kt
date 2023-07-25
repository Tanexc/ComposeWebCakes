package domain.use_case

import domain.repository.ClientRepository
import domain.model.Message
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMessagesUseCase: KoinComponent {
    private val clientRepository: ClientRepository<HttpClient> by inject()

    operator fun invoke(): Flow<List<Message>> = clientRepository.getMessages()
}