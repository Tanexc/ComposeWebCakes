package domain.use_case

import domain.repository.ClientRepository
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUserIdUseCase: KoinComponent {
    private val clientRepository: ClientRepository<HttpClient> by inject()

    operator fun invoke(): Flow<String> = clientRepository.getUserId()
}