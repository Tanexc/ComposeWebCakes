package domain.use_case

import domain.interfaces.ClientRepository
import domain.model.Message
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostMessageUseCase: KoinComponent {
    val clientRepository: ClientRepository by inject()

    operator fun invoke(message: Message) {
        clientRepository.postMessage(message)
    }
}