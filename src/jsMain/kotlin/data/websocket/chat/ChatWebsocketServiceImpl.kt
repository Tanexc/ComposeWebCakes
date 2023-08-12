package data.websocket.chat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import constants.Application.HOST
import constants.Websocket.CHAT_WEBSOCKET
import domain.interfaces.WebsocketService
import domain.model.Message
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.component.inject
import util.exceptions.WebsocketWasClosed

class ChatWebsocketServiceImpl : WebsocketService<Message> {
    private val client: HttpClient by inject()

    private val _session: MutableState<DefaultClientWebSocketSession?> = mutableStateOf(null)
    private val session: DefaultClientWebSocketSession? by _session

    suspend operator fun invoke(
        clientId: String,
        clientName: String,
    ): Flow<Message>? {

        _session.value = client.webSocketSession(
            host = HOST,
            path = "$CHAT_WEBSOCKET?clientId=$clientId&clientName=$clientName"
        )

        return session
            ?.let { session ->
                session.incoming
                    .receiveAsFlow()
                    .map {
                        session.converter!!.deserialize(it)
                    }
            }
    }

    suspend operator fun invoke(
        clientId: String,
        clientName: String,
        userId: Long
    ): Flow<Message>? {

        _session.value = client.webSocketSession(
            host = HOST,
            path = "$CHAT_WEBSOCKET?clientId=$clientId&clientName=$clientName&userId=$userId"
        )

        println(session)

        return session
            ?.let { session ->
                session.incoming
                    .receiveAsFlow()
                    .map {
                        session.converter!!.deserialize(it)
                    }
            }
    }

    override suspend fun close() {
        session?.close(CloseReason(CloseReason.Codes.NORMAL, ""))
        _session.value = null
    }

    override suspend fun send(value: Message) {
        session?.sendSerialized(value) ?: throw WebsocketWasClosed()
    }
}