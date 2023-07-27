package ru.tanexc.application.data.repository

import domain.model.User
import io.ktor.util.date.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.tanexc.application.core.util.JwtTool.generateToken
import ru.tanexc.application.core.util.JwtTool.isNotExpired
import ru.tanexc.application.core.util.PasswordHashTool.getHash
import ru.tanexc.application.core.util.PasswordHashTool.verifyHash
import ru.tanexc.application.core.util.validatiors.PasswordValidator.isValid
import ru.tanexc.application.domain.interfaces.UserDao
import ru.tanexc.application.domain.repository.UserRepository
import util.State
import util.exceptions.*

class UserRepositoryImpl : UserRepository, KoinComponent {
    private val userDao: UserDao by inject()

    override suspend fun signUp(data: User, password: String): Flow<State<User>> = flow {
        try {
            emit(State.Processing())

            if (!userDao.isLoginAvailable(data.login)) throw InvalidData()

            val token = generateToken(
                name = data.name,
                surname = data.surname,
                login = data.login,
                oldToken = data.token,
                creationTimestamp = getTimeMillis()
            )

            val passwordHash = getHash(password)

            val user: User = userDao.insert(
                data.copy(
                    token = token,
                    password = passwordHash,
                    creationTimestamp = getTimeMillis()
                )
            ) ?: throw DataIsNull()

            emit(State.Success(user))

        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "sign up problem"))
        }
    }

    override suspend fun signIn(login: String, password: String): Flow<State<User>> = flow {
        try {
            emit(State.Processing())

            var user: User = userDao.getByLogin(login) ?: throw WrongCredentials("login")
            if (!verifyHash(password, user.password)) throw WrongCredentials("password")

            if (!user.token.isNotExpired()) {
                val token = generateToken(
                    name = user.name,
                    surname = user.surname,
                    login = user.login,
                    oldToken = user.token,
                    creationTimestamp = getTimeMillis()
                )
                user = user.copy(token = token)
                userDao.edit(user)
            }

            emit(State.Success(user))

        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "sign in problem"))
        }
    }

    override suspend fun getByToken(data: String): Flow<State<User>> = flow {
        try {
            emit(State.Processing())

            if (!data.isNotExpired()) throw TokenExpired()
            val user: User = userDao.getByToken(data)?: throw DataIsNull()
            emit(State.Success(user))

        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "token authorization problem"))
        }
    }

    override suspend fun getById(id: Long): Flow<State<User>> = flow {
        try {
            emit(State.Processing())
            val user: User = userDao.getById(id) ?: throw DataIsNull()
            emit(State.Success(user))

        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "getting info by id=$id problem"))
        }
    }

    override suspend fun updateToken(data: User): Flow<State<User>> = flow {
        try {
            emit(State.Processing())

            val user: User = userDao.getByToken(data.token)?: throw InvalidData()
            val token = generateToken(
                name = data.name,
                surname = data.surname,
                login = data.login,
                oldToken = data.token,
                creationTimestamp = getTimeMillis()
            )
            userDao.edit(user.copy(token = token))

            emit(State.Success(user.copy(token = token)))
        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "update token problem"))
        }
    }

    override suspend fun updatePassword(data: User, newPassword: String): Flow<State<User>> = flow {
        try {
            emit(State.Processing())

            val user: User = userDao.getByToken(data.token)?: throw InvalidData()
            if (!newPassword.isValid()) throw InvalidData()
            val password = getHash(newPassword)
            userDao.edit(user.copy(password = password))

            emit(State.Success(user.copy(password = password)))
        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "update password problem"))
        }
    }

    override suspend fun updateChatIds(data: User, id: Long): Flow<State<User>>  = flow {
        try {
            emit(State.Processing())

            if (!data.token.isNotExpired()) throw TokenExpired()
            val user: User = userDao.getByToken(data.token)?: throw InvalidData()

            userDao.edit(user.copy(chatIds = user.chatIds + id))
            emit(State.Success(user.copy(chatIds = user.chatIds + id)))
        } catch (e: Exception) {
            emit(State.Error(message = e.message ?: "update chats problem"))
        }
    }
}