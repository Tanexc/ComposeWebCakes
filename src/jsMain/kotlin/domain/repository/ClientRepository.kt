package domain.repository

import domain.model.Message
import domain.model.Theme
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface ClientRepository<T>: KoinComponent {
    val client: T

    fun getUserId(): Flow<String>

    fun getTheme(): Flow<Theme>

    fun setTheme(theme: Theme)

    fun getMessages(): Flow<List<Message>>

    fun getMessage(id: Long): Flow<Message>

    fun postMessage(message: Message)

    fun getLocale(): Int

    fun setLocale(localeId: Int)

}