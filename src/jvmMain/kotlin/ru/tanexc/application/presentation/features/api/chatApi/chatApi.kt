package ru.tanexc.application.presentation.features.api.chatApi

import constants.Api.GET_ALL_CHATS
import constants.Api.GET_CHAT
import constants.Api.GET_NEW_CHAT
import constants.Api.POST_MESSAGE_INTO_CHAT
import domain.model.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.koin.ktor.ext.inject
import ru.tanexc.application.domain.use_cases.chat_use_cases.*
import ru.tanexc.application.domain.use_cases.user_use_cases.UserGetByTokenUseCase
import util.State
import util.exceptions.DataIsNull
import util.exceptions.InvalidData

fun Routing.chatApi() {
    val chatCreateUseCase: ChatCreateUseCase by inject()
    val chatGetByIdUseCase: ChatGetByIdUseCase by inject()
    val chatGetByClientIdUseCase: ChatGetByClientIdUseCase by inject()
    val chatInsertMessageUseCase: ChatInsertMessageUseCase by inject()
    val chatGetAllUseCase: ChatGetAllUseCase by inject()

    val userGetByTokenUseCase: UserGetByTokenUseCase by inject()

    get(GET_CHAT) {
        try {
            val clientId: String = call.request.queryParameters["clientId"] ?: throw InvalidData()
            chatGetByClientIdUseCase(clientId).collect {

                when (it) {
                    is State.Success -> {
                        call.respond(RespondData(data = it.data))
                    }

                    is State.Error -> {
                        throw DataIsNull()
                    }

                    else -> {}
                }

            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }

    }

    get(GET_NEW_CHAT) {

        try {
            val clientId: String = call.request.queryParameters["clientId"] ?: throw InvalidData()
            val userName: String = call.request.queryParameters["userName"] ?: throw InvalidData()

            chatCreateUseCase(clientId, userName).collect {

                when (it) {
                    is State.Success -> {
                        call.respond(RespondData(data = it.data as Domain))
                    }

                    is State.Error -> {
                        throw DataIsNull()
                    }

                    else -> {}
                }

            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }

    }

    post(POST_MESSAGE_INTO_CHAT) {
        try {
            var chatId: Long? = null

            val multipart = call.receiveMultipart().readAllParts()
            multipart.filterIsInstance<PartData.FormItem>().forEach {
                when (it.name) {
                    "chatId" -> chatId = it.value.toLong()
                }
            }

            if (chatId == null) throw InvalidData()

            val message: Message = call.receive()

            chatGetByIdUseCase(chatId!!).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { chat ->
                            chatInsertMessageUseCase(chat, message).collect { state ->
                                when (state) {
                                    is State.Success -> {
                                        call.respond(RespondData(state.data))
                                    }

                                    is State.Error -> {
                                        throw DataIsNull()
                                    }

                                    else -> {}
                                }
                            }
                        } ?: throw DataIsNull()
                    }

                    is State.Error -> {
                        throw DataIsNull()
                    }

                    else -> {}
                }

            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }

    }

    get(GET_ALL_CHATS) {

        try {
            val token: String = call.request.queryParameters["token"] ?: throw InvalidData()

            chatGetAllUseCase(token).collect {state ->
                when (state) {
                    is State.Error -> throw Exception()
                    is State.Success -> call.respond(RespondData(state.data))
                    else -> {}
                }
            }

        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }


    }

}