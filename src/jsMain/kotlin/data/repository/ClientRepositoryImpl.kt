package data.repository


import core.util.HashTool.generateClientId
import domain.model.Theme
import domain.repository.ClientRepository
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        localStorage.setItem("name", data)
    }

    override fun getTheme(): Theme {
        var themeId = localStorage.getItem("themeId")?.toLongOrNull()
        var useDarkTheme = localStorage.getItem("useDarkTheme")?.toBoolean()
        if (useDarkTheme == null) {
            useDarkTheme = true
            localStorage.setItem("useDarkTheme", "$useDarkTheme")
        }

        if (themeId == null) {
            themeId = 0
            localStorage.setItem("themeId", "$themeId")
        }

        return Theme(themeId, useDarkTheme)
    }

    override fun setTheme(value: Theme) {
        localStorage.setItem("useDarkTheme", "${value.useDarkTheme}")
        localStorage.setItem("themeId", "${value.id}")
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

    override fun getToken(): String? = localStorage.getItem("token")

    override fun setToken(value: String) {
        localStorage.setItem("token", value)
    }
}