package ru.tanexc.application.domain.use_cases.user_use_cases

import domain.model.User
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.domain.repository.UserRepository
import util.State

class UserUpdateChatIdsUseCase: KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(token: String, id: Long): Flow<State<User>> = repository.updateChatIds(token, id)
}