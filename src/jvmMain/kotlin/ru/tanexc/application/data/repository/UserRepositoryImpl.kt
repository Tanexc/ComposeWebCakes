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
import ru.tanexc.application.data.database.entity.UserTable
import ru.tanexc.application.domain.interfaces.UserDao
import ru.tanexc.application.domain.repository.UserRepository
import util.State

class UserRepositoryImpl : UserRepository, KoinComponent {
    private val userDao: UserDao by inject()

    override suspend fun signUp(data: User, password: String): Flow<State<User>> = flow {
        emit(State.Processing())

        val token = generateToken(
            name = data.name,
            surname = data.surname,
            login = data.login,
            oldToken = data.token,
            creationTimestamp = getTimeMillis()
        )

        val passwordHash = getHash(password)

        val user: User? = userDao.insert(
            data.copy(
                token = token,
                password = passwordHash,
                creationTimestamp = getTimeMillis()
            )
        )

        if (user == null) {
            emit(State.Error(message = "user not found"))
        } else {
            emit(State.Success(user))
        }
    }

    override suspend fun signIn(login: String, password: String): Flow<State<User>> = flow {
        emit(State.Processing())
        var user: User? = userDao.getByLogin(login)

        if (user == null) {
            emit(State.Error())
        } else {
            if (verifyHash(password, user.password)) {

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

            } else {
                emit(State.Error(message = "wrong credentials"))
            }
        }
    }

    override suspend fun getByToken(data: String): Flow<State<User>> = flow {
        emit(State.Processing())

        if (data.isNotExpired()) {
            val user: User? = userDao.getByToken(data)

            if (user == null) {
                emit(State.Error(message = "wrong token"))
            } else {
                emit(State.Success(user))
            }
        } else {
            emit(State.Expired(message = "token expired"))
        }
    }

    override suspend fun getById(id: Long): Flow<State<User>> = flow {
        emit(State.Processing())
        val user: User? = userDao.getById(id)

        if (user == null) {
            emit(State.Error(message = "user not found"))
        } else {
            emit(State.Success(user))
        }

    }

    override suspend fun updateToken(data: User): Flow<State<User>> = flow {
        emit(State.Processing())

        if (userDao.getById(data.id) != null) {
            val token = generateToken(
                name = data.name,
                surname = data.surname,
                login = data.login,
                oldToken = data.token,
                creationTimestamp = getTimeMillis()
            )
            val user = data.copy(token = token)
            userDao.edit(user)

            emit(State.Success())
        } else {
            emit(State.Error(message = "user not found"))
        }
    }

    override suspend fun updatePassword(data: User): Flow<State<User>> = flow {

    }

    override suspend fun updateChatIds(id: Long): Flow<State<User>> {
        TODO("Not yet implemented")
    }
}