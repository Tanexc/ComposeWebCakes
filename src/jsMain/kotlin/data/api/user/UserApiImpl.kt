package data.api.user

import constants.Api.GET_USER
import constants.Api.GET_USER_BY_TOKEN
import constants.Api.SIGN_IN_USER
import constants.Api.SIGN_UP_USER
import constants.Api.UPDATE_CHATS_USER
import constants.Api.UPDATE_PASSWORD
import constants.Application.HOST
import domain.interfaces.UserApi
import domain.model.RespondData
import domain.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import org.koin.core.component.inject

class UserApiImpl : UserApi {
    private val client: HttpClient by inject()

    @OptIn(InternalAPI::class)
    override suspend fun signUp(data: User, password: String): RespondData<User> {
        println("COCK")
        val cock: RespondData<User> = client.post(urlString = "http://${HOST}/${SIGN_UP_USER}") {
            body = MultiPartFormDataContent(
                formData {
                    append("name", data.name)
                    append("surname", data.name)
                    append("password", password)
                    append("login", data.login)
                }
            )
        }.body()
        println(cock)
        return cock
    }

    override suspend fun signIn(login: String, password: String): RespondData<User> =
        client.get(urlString = "http://${HOST}/${SIGN_IN_USER}") {
            url.parameters.appendAll(
                parametersOf(
                    "login" to listOf(login),
                    "password" to listOf(password)
                )
            )
        }.body()


    override suspend fun getByToken(token: String): RespondData<User> =
        client.get(urlString = "http://${HOST}/${GET_USER_BY_TOKEN}") {
            url.parameters.append(
                "token", token
            )
        }.body()


    override suspend fun getById(id: Long): RespondData<User> =
        client.get(urlString = "http://${HOST}/${GET_USER}") {
            url.parameters.append(
                "userId", id.toString()
            )
        }.body()

    override suspend fun updatePassword(token: String, newPassword: String): RespondData<User> =
        client.post(urlString = "http://${HOST}/${UPDATE_PASSWORD}") {
            url.parameters.appendAll(
                parametersOf(
                    "token" to listOf(token),
                    "newPassword" to listOf(newPassword)
                )
            )
        }.body()

    @OptIn(InternalAPI::class)
    override suspend fun updateChatIds(token: String, id: Long): RespondData<User> =
        client.post(urlString = "http://${HOST}/${UPDATE_CHATS_USER}") {
            body = MultiPartFormDataContent(
                formData {
                    append("token", token),
                    append("chatId", id.toString())
                }
            )
        }.body()
}