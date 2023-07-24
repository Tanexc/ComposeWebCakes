package ru.tanexc.application.domain.model

import domain.model.Domain
import org.jetbrains.exposed.sql.ResultRow

interface DatabaseEntity {
    fun asDomain(data: ResultRow): Domain
}