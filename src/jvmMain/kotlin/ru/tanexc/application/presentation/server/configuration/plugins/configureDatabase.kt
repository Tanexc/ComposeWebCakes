package ru.tanexc.application.presentation.server.configuration.plugins

import io.ktor.server.application.*
import ru.tanexc.application.data.database.factory.DatabaseFactory

fun Application.configureDatabase() {
    DatabaseFactory.init(environment.config)
}