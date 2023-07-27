package ru.tanexc.application.domain.interfaces

import domain.model.User

interface UserDao {

    suspend fun insert(user: User): User?

    suspend fun getById(id: Long): User?

    suspend fun edit(user: User)

    suspend fun getByToken(token: String): User?

    suspend fun getByLogin(login: String): User?

    suspend fun isLoginAvailable(login: String): Boolean

}