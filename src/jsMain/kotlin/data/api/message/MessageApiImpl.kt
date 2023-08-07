package data.api.message

import constants.Api
import constants.Application
import domain.interfaces.MessageApi
import domain.model.Message
import domain.model.RespondData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.inject

class MessageApiImpl: MessageApi {
    private val client: HttpClient by inject()

    override suspend fun getById(id: Long): RespondData<Message> =
        client.get(urlString = "http://${Application.HOST}/${Api.GET_MESSAGE}") {
            url.parameters.appendAll(
                parametersOf(
                    "id" to listOf(id.toString())
                )
            )
        }.body()

    override suspend fun getByClientId(clientId: String): RespondData<List<Message>> =
        client.get(urlString = "http://${Application.HOST}/${Api.GET_MESSAGES}") {
            url.parameters.appendAll(
                parametersOf(
                    "clientId" to listOf(clientId)
                )
            )
        }.body()
}