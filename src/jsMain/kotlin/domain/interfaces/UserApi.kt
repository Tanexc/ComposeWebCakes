package domain.interfaces

import domain.model.RespondData
import domain.model.User
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import util.State

interface UserApi: KoinComponent {
    suspend fun signUp(data: User, password: String): RespondData<User>

    suspend fun signIn(login: String, password: String): RespondData<User>

    suspend fun getByToken(token: String): RespondData<User>

    suspend fun getById(id: Long): RespondData<User>

    suspend fun updatePassword(token: String, newPassword: String): RespondData<User>

    suspend fun updateChatIds(token: String, id: Long): RespondData<User>
}