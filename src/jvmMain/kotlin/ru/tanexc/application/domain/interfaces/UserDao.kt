package ru.tanexc.application.domain.interfaces

import domain.model.User

interface UserDao {

    suspend fun insert(user: User): User?

    suspend fun getById(id: Long): User?

    suspend fun edit(user: User)


}