package ru.tanexc.application.presentation.features.api.messageApi

import constants.Api.GET_MESSAGE
import constants.Api.GET_MESSAGES
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.tanexc.application.core.util.RespondData
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatGetByClientIdUseCase
import ru.tanexc.application.domain.use_cases.message_use_cases.MessageGetByIdUseCase
import ru.tanexc.application.domain.use_cases.message_use_cases.MessageGetByListUseCase
import util.State
import util.exceptions.DataIsNull
import util.exceptions.InvalidData

fun Routing.messageApi() {
    val getMessagesUseCase: MessageGetByListUseCase by inject()
    val getMessageUseCase: MessageGetByIdUseCase by inject()
    val getChatByClientIdUseCase: ChatGetByClientIdUseCase by inject()

    get(GET_MESSAGES) {

        try {
            val clientId: String = call.request.queryParameters["clientId"] ?: throw InvalidData()

            getChatByClientIdUseCase(clientId).collect {
                when (it) {
                    is State.Success -> {
                        val data: List<Long> = it.data?.messages ?: throw DataIsNull()
                        getMessagesUseCase(data).collect { state ->
                            when (state) {
                                is State.Success -> {

                                    state.data?.let { messages ->
                                        call.respond(RespondData(messages))
                                    } ?: throw DataIsNull()

                                }

                                is State.Error -> {
                                    throw Exception(state.message)
                                }

                                else -> {}
                            }
                        }
                    }

                    is State.Error -> {
                        throw Exception(it.message)
                    }

                    else -> {}
                }
            }


        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    get(GET_MESSAGE) {
        try {
            val id: Long = call.request.queryParameters["id"]?.toLong() ?: throw InvalidData()

            getMessageUseCase(id).collect { state ->
                when (state) {
                    is State.Success -> {

                        state.data?.let { message ->
                            call.respond(RespondData(message))
                        } ?: throw DataIsNull()

                    }

                    is State.Error -> {
                        throw Exception(state.message)
                    }

                    else -> {}
                }
            }

        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }
}