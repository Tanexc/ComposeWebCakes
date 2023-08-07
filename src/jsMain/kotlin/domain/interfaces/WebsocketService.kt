package domain.interfaces

import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface WebsocketService<T> : KoinComponent {
    suspend fun send(value: T)

    suspend fun close()
}