package ru.tanexc.application.domain.interfaces

import domain.model.Domain
import domain.model.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

interface DatabaseEntity {
    suspend fun asDomain(getResult: suspend (Table) -> ResultRow?): Domain?
}