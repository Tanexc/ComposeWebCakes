package ru.tanexc.application.presentation.server.configuration.plugins

import domain.model.Session
import io.ktor.server.sessions.*
import io.ktor.server.application.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<Session>("SESSION")
    }
}
