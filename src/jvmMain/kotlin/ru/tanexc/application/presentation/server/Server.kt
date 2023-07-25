package ru.tanexc.application.presentation.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.tanexc.application.data.database.factory.DatabaseFactory
import ru.tanexc.application.presentation.server.configuration.plugins.*
import ru.tanexc.application.presentation.server.configuration.routes.configureRoutes

fun main() {
    embeddedServer(
        Netty,
        host = "",
        module = {
            // ROUTES
            configureRoutes()

            // PLUGINS
            configureDatabase()
            configureDi()
            configureSecurity()
            configureSockets()
            configureSerialization()


        }
    ).start(wait = true)
}