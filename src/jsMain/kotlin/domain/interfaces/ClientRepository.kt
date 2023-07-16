package domain.interfaces

import domain.model.Message
import domain.model.Theme
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface ClientRepository: KoinComponent {
    val client: HttpClient

    fun getUserId(): Flow<String>

    fun getTheme(): Flow<Theme>

    fun setTheme(theme: Theme)

    fun getMessages(): Flow<List<Message>>

    fun getMessage(id: Long): Flow<Message>

    fun postMessage(message: Message)

}