package data.api.chat

import constants.Api
import constants.Application
import domain.interfaces.ChatApi
import domain.model.Chat
import domain.model.RespondData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.inject

class ChatApiImpl : ChatApi {
    private val client: HttpClient by inject()

    override suspend fun getChatByClientId(clientId: String): RespondData<Chat> =
        client.get(urlString = "http://${Application.HOST}/${Api.GET_CHAT}") {
            url.parameters.appendAll(
                parametersOf(
                    "clientId" to listOf(clientId)
                )
            )
        }.body()

    override suspend fun createChat(clientId: String, title: String): RespondData<Chat> =
        client.get(urlString = "http://${Application.HOST}/${Api.GET_NEW_CHAT}") {
            url.parameters.appendAll(
                parametersOf(
                    "clientId" to listOf(clientId),
                    "title" to listOf(title)
                )
            )
        }.body()
}