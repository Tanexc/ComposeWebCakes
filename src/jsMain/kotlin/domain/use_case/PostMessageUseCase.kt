package domain.use_case

import domain.model.Message
import domain.repository.ClientRepository
import io.ktor.client.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostMessageUseCase: KoinComponent {
    private val clientRepository: ClientRepository<HttpClient> by inject()

    operator fun invoke(message: Message) {
        clientRepository.postMessage(message)
    }
}