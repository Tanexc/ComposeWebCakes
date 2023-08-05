package data.api.chat

import constants.Api
import constants.Application
import domain.interfaces.ChatApi
import domain.model.Chat
import domain.model.Message
import domain.model.RespondData
import domain.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.inject
import util.State

class ChatApiImpl: ChatApi {
    private val client: HttpClient by inject()

    override suspend fun getChatByClientId(clientId: String): RespondData<Chat> =
        client.get(urlString = "http://${Application.HOST}${Api.GET_CHAT}") {
            url.parameters.appendAll(
                parametersOf(
                    "clientId" to listOf(clientId)
                )
            )
        }.body()

    @OptIn(InternalAPI::class)
    override suspend fun createChat(clientId: String, title: String): RespondData<Chat> =
        client.post(urlString = "http://${Application.HOST}${Api.GET_NEW_CHAT}") {
            body = FormDataContent(
                Parameters.build {
                    append("clientId", clientId)
                    append("title", title)
                }
            )
        }.body()
}