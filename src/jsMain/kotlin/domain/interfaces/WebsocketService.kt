package domain.interfaces

import org.koin.core.component.KoinComponent

interface WebsocketService<T> : KoinComponent {
    suspend fun send(value: T)

    suspend fun close()
}