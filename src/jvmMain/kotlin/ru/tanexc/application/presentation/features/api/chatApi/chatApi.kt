package ru.tanexc.application.presentation.features.api.chatApi

import constants.Api.GET_CHAT
import constants.Api.GET_NEW_CHAT
import constants.Api.POST_MESSAGE_INTO_CHAT
import domain.model.Message
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.collect
import org.koin.ktor.ext.inject
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatCreateUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatGetByClientIdUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatGetByIdUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatInsertMessageUseCase
import util.RespondData
import util.State
import util.exceptions.DataIsNull
import util.exceptions.InvalidData

fun Routing.chatApi() {
    val chatCreateUseCase: ChatCreateUseCase by inject()
    val chatGetByIdUseCase: ChatGetByIdUseCase by inject()
    val chatGetByClientIdUseCase: ChatGetByClientIdUseCase by inject()
    val chatInsertMessageUseCase: ChatInsertMessageUseCase by inject()

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
            call.respond(RespondData(message = e.message))
        }

    }

    get(GET_NEW_CHAT) {

        try {
            val clientId: String = call.request.queryParameters["clientId"] ?: throw InvalidData()
            val userName: String = call.request.queryParameters["userName"] ?: throw InvalidData()

            chatCreateUseCase(clientId, userName).collect {

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
            call.respond(RespondData(message = e.message))
        }

    }

    post(POST_MESSAGE_INTO_CHAT) {
        try {
            val chatId: Long = call.request.queryParameters["chatId"]?.toLong() ?: throw InvalidData()
            val message: Message = call.receive()

            chatGetByIdUseCase(chatId).collect {
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
                        }?: throw DataIsNull()
                    }

                    is State.Error -> {
                        throw DataIsNull()
                    }

                    else -> {}
                }

            }
        } catch (e: Exception) {
            call.respond(RespondData(message = e.message))
        }

    }

}