package data.repository

import domain.interfaces.UserApi
import domain.model.User
import domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.inject
import util.State

class UserRepositoryImpl: UserRepository {
    private val userApi: UserApi by inject()

    override suspend fun signUp(data: User, password: String): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.signUp(data, password)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "sign up error"))
            }
        }
    }

    override suspend fun signIn(login: String, password: String): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.signIn(login, password)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "sign in error"))
            }
        }
    }

    override suspend fun getByToken(data: String): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.getByToken(data)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get by token error"))
            }
        }
    }

    override suspend fun getById(id: Long): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.getById(id)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "get by id error"))
            }
        }
    }

    override suspend fun updatePassword(token: String, newPassword: String): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.updatePassword(token, newPassword)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "update password error"))
            }
        }
    }

    override suspend fun updateChatIds(token: String, id: Long): Flow<State<User>> = flow {
        emit(State.Processing())
        val respond = userApi.updateChatIds(token, id)
        when(val user = respond.data) {
            is User -> {
                emit(State.Success(user))
            }
            else -> {
                emit(State.Error(message = respond.message?: "update chat ids error"))
            }
        }
    }
}