package domain.use_case

import domain.interfaces.ClientRepository
import domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUserIdUseCase: KoinComponent {
    private val clientRepository: ClientRepository by inject()

    operator fun invoke(): Flow<String> = clientRepository.getUserId()
}