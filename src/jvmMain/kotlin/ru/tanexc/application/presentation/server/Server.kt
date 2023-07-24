package ru.tanexc.application.presentation.server

import ru.tanexc.application.presentation.server.configuration.plugins.configureSecurity
import ru.tanexc.application.presentation.server.configuration.plugins.configureSerialization
import ru.tanexc.application.presentation.server.configuration.plugins.configureSockets
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.tanexc.application.presentation.server.configuration.plugins.configureDi
import ru.tanexc.application.presentation.server.configuration.routes.configureRoutes

fun main() {
    embeddedServer(
        Netty,
        host = "",
        module = {
            // ROUTES
            configureRoutes()

            // PLUGINS
            configureDi()
            configureSecurity()
            configureSockets()
            configureSerialization()

        }
    ).start(wait = true)
}