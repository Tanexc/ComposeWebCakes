package ru.tanexc.application.presentation.websocket.chatWebsocket

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import ru.tanexc.application.presentation.websocket.controller.ChatConnectionController
import util.exceptions.Disconnected


fun Routing.chatWebsocket() {
    val connectionController: ChatConnectionController by inject()

    webSocket("/websocket/chat") {
        try {
            connectionController.connection(this, call.parameters)
        } catch (e: Disconnected) {
            this.close(
                reason = CloseReason(
                    CloseReason.Codes.NORMAL,
                    message = e.message
                )
            )
        } catch (e: Exception) {
            this.close(
                reason = CloseReason(
                    CloseReason.Codes.TRY_AGAIN_LATER,
                    message = e.message ?: "websocket error"
                )
            )
        }
    }
}
