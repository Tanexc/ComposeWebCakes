package data.repository


import constants.Api.GET_MESSAGE
import constants.Api.GET_MESSAGES
import constants.Application.HOST
import core.util.HashTool.generateClientId
import domain.interfaces.ChatApi
import domain.interfaces.UserApi
import domain.model.Message
import domain.model.Theme
import domain.repository.ClientRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.inject

class ClientRepositoryImpl : ClientRepository {
    override fun getClientId(): Flow<String> = flow {
        var userId: String? = localStorage.getItem("userId")
        if (userId == null) {
            userId = generateClientId()
            localStorage.setItem(
                "userId",
                userId
            )
        }
        emit(userId)
    }

    override fun getClientName(): String? = localStorage.getItem("name")

    override fun setClientName(data: String) {
        localStorage.setItem("name", "data")
    }

    override fun getTheme(): Flow<Theme> = flow {
        var themeId = localStorage.getItem("themeId")?.toLong()
        var useDarkTheme = localStorage.getItem("useDarkTheme")?.toBoolean()
        if (useDarkTheme == null) {
            useDarkTheme = true
            localStorage.setItem("useDarkTheme", "$useDarkTheme")
        }

        if (themeId == null) {
            themeId = 0
            localStorage.setItem("themeId", "$themeId")
        }

        emit(Theme(themeId, useDarkTheme))
    }

    override fun setTheme(theme: Theme) {
        localStorage.setItem("useDarkTheme", "${theme.useDarkTheme}")
        localStorage.setItem("themeId", "${theme.id}")
    }

    override fun getLocale(): Int {
        var locale = localStorage.getItem("locale")?.toInt()
        if (locale == null) {
            locale = 0
            setLocale(locale)
        }
        return locale
    }

    override fun setLocale(localeId: Int) {
        localStorage.setItem("locale", "$localeId")
    }
}