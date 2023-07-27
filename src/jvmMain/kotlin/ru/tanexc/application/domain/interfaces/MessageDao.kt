package ru.tanexc.application.domain.interfaces

import domain.model.Message

interface MessageDao {
    suspend fun insert(message: Message): Message?

    suspend fun getById(id: Long): Message?

    suspend fun getByIdList(listId: List<Long>): List<Message>
}