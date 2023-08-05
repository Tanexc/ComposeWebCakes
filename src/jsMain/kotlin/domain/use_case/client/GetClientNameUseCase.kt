package domain.use_case.client

import domain.repository.ClientRepository
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetClientNameUseCase: KoinComponent {
    private val clientRepository: ClientRepository by inject()

    operator fun invoke(): String? = clientRepository.getClientName()
}