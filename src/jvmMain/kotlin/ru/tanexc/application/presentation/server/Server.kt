package ru.tanexc.application.presentation.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.tanexc.application.core.util.config.ConfigTool
import ru.tanexc.application.presentation.server.configuration.plugins.*
import ru.tanexc.application.presentation.server.configuration.routes.configureRoutes

fun main() {
    val configTool = ConfigTool()

    embeddedServer(
        Netty,
        host = configTool.getProperty("ktor.deployment.host")!!,
        port = configTool.getProperty("ktor.deployment.port")!!.toInt(),
        module = {
            // PLUGINS
            configureDi()
            configureSecurity()
            configureSockets()
            configureSerialization()
            configureDatabase()

            // ROUTES
            configureRoutes()
        }
    ).start(wait = true)
}