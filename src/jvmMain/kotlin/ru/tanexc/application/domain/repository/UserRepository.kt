package ru.tanexc.application.domain.repository

import domain.model.User
import kotlinx.coroutines.flow.Flow
import util.State

interface UserRepository {

    suspend fun signUp(data: User, password: String): Flow<State<User>>

    suspend fun signIn(login: String, password: String): Flow<State<User>>

    suspend fun getByToken(data: String): Flow<State<User>>

    suspend fun getById(id: Long): Flow<State<User>>

    suspend fun updateToken(data: User): Flow<State<User>>

    suspend fun updatePassword(data: User, newPassword: String): Flow<State<User>>

    suspend fun updateChatIds(data: User, id: Long): Flow<State<User>>

}