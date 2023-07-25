package data


import constants.Api.GET_MESSAGE
import constants.Api.GET_MESSAGES
import constants.Deployment.HOST
import core.util.HashTool.generateUserId
import domain.interfaces.StringResources
import domain.repository.ClientRepository
import domain.model.Message
import domain.model.Theme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.inject

class ClientRepositoryImpl : ClientRepository<HttpClient> {
    override val client: HttpClient by inject()

    override fun getUserId(): Flow<String> = flow {
        var userId: String? = localStorage.getItem("userId")
        if (userId == null) {
            userId = generateUserId()
            localStorage.setItem(
                "userId",
                userId
            )
        }
        emit(userId)
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

    override fun getMessages(): Flow<List<Message>> = flow {
        val response = client.get("$HOST$GET_MESSAGES")

        val messages: List<Message> = response.body()
        emit(messages)
    }

    override fun getMessage(id: Long): Flow<Message> = flow {
        val response = client.get("$HOST$GET_MESSAGE") {
            url {
                parameters.append("id", "$id")
            }
        }

        val message: Message = response.body()
        emit(message)
    }

    override fun postMessage(message: Message) {
        TODO("Not yet implemented")
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