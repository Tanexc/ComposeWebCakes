package ru.tanexc.application.domain.interfaces

import domain.model.Chat

interface ChatDao {
    suspend fun insert(chat: Chat): Chat?

    suspend fun getById(id: Long): Chat?

    suspend fun edit(chat: Chat)
}