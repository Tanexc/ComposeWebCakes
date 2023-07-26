package ru.tanexc.application.presentation.server.configuration.plugins

import domain.model.Session
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<Session>("SESSION")
    }
}
