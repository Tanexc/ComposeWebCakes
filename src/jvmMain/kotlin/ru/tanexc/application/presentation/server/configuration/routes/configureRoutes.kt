package ru.tanexc.application.presentation.server.configuration.routes


import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import ru.tanexc.application.presentation.features.api.chatApi.chatApi
import ru.tanexc.application.presentation.features.api.messageApi.messageApi
import ru.tanexc.application.presentation.features.api.userApi.userApi
import ru.tanexc.application.presentation.features.websocket.chatWebsocket.chatWebsocket
import ru.tanexc.application.presentation.ui.baseUiRoute

fun Application.configureRoutes() = routing {

    // UI
    baseUiRoute()

    // API
    chatApi()
    messageApi()
    userApi()

    // WEBSOCKETS
    chatWebsocket()

    // STATIC FILES
    staticResources(
        remotePath = "/static",
        basePackage = null
    )
}
