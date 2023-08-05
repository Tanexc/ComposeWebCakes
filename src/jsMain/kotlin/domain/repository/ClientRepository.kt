package domain.repository

import domain.model.Message
import domain.model.Theme
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface ClientRepository: KoinComponent {

    fun getClientId(): Flow<String>

    fun getClientName(): String?

    fun setClientName(data: String)

    fun getTheme(): Flow<Theme>

    fun setTheme(theme: Theme)

    fun getLocale(): Int

    fun setLocale(localeId: Int)

}