package ru.tanexc.application.domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow
import util.State

interface MessageRepository {

    suspend fun create(data: Message): Flow<State<Message>>

    suspend fun getById(id: Long): Flow<State<Message?>>

    suspend fun getByListId(listId: List<Long>): Flow<State<List<Message>>>

}