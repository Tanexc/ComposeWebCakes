package domain.use_case.user

import domain.model.User
import domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class UserUpdatePasswordUseCase: KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(token: String, newPassword: String): Flow<State<User>> = repository.updatePassword(token, newPassword)
}