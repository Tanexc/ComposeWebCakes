package ru.tanexc.application.domain.repository

import domain.model.Chat
import domain.model.User
import kotlinx.coroutines.flow.Flow
import util.State

interface UserRepository {

    suspend fun create(user: User): Flow<State<User>>

    suspend fun getByToken(token: String): Flow<State<User>>

    suspend fun getByPassword(password: String): Flow<State<User>>

    suspend fun getById(id: Long): Flow<State<User>>

    suspend fun updateToken(user: User): Flow<State<User>>

    suspend fun updatePassword(user: User): Flow<State<User>>

    suspend fun updateChatIds(chatId: Chat): Flow<State<User>>

}