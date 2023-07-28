package ru.tanexc.application.presentation.server.configuration.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import ru.tanexc.application.presentation.api.chatApi.chatApiRoutes
import ru.tanexc.application.presentation.ui.baseUiRoute
import ru.tanexc.application.presentation.websocket.chatWebsocket.chatWebsocket

fun Application.configureRoutes() = routing {

    // UI
    baseUiRoute()

    // API
    chatApiRoutes()

    // WEBSOCKETS
    chatWebsocket()

    // STATIC FILES
    staticResources(
        remotePath = "/static",
        basePackage = null
    )
}
