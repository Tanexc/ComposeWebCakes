package ru.tanexc.application.presentation.features.api.userApi

import constants.Api.GET_USER
import constants.Api.GET_USER_BY_TOKEN
import constants.Api.SIGN_IN_USER
import constants.Api.SIGN_UP_USER
import constants.Api.UPDATE_CHATS_USER
import constants.Api.UPDATE_PASSWORD
import domain.model.Domain
import domain.model.RespondData
import domain.model.User
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.tanexc.application.domain.use_cases.user_use_cases.*
import util.State
import util.exceptions.DataIsNull
import util.exceptions.InvalidData

fun Routing.userApi() {
    val userGetByIdUseCase: UserGetByIdUseCase by inject()
    val userGetByTokenUseCase: UserGetByTokenUseCase by inject()
    val userSignInUseCase: UserSignInUseCase by inject()
    val userSignUpUseCase: UserSignUpUseCase by inject()
    val userUpdateChatIdsUseCase: UserUpdateChatIdsUseCase by inject()
    val userUpdatePasswordUseCase: UserUpdatePasswordUseCase by inject()

    get(GET_USER) {
        try {

            val userId: Long = call.request.queryParameters["userId"]?.toLong() ?: throw InvalidData()

            userGetByIdUseCase(userId).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user as Domain))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    get(GET_USER_BY_TOKEN) {
        try {

            val token: String = call.request.queryParameters["token"]?: throw InvalidData()

            userGetByTokenUseCase(token).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user as Domain))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    get(SIGN_IN_USER) {
        try {
            val login: String = call.request.queryParameters["login"] ?: throw InvalidData()
            val password: String = call.request.queryParameters["password"] ?: throw InvalidData()

            userSignInUseCase(login, password).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    post(SIGN_UP_USER) {
        try {
            var login: String? = null
            var name: String? = null
            var surname: String? = null
            var password: String? = null

            val multipart = call.receiveMultipart().readAllParts()
            multipart.filterIsInstance<PartData.FormItem>().forEach {
                when (it.name) {
                    "login" -> login = it.value
                    "name" -> name = it.value
                    "surname" -> surname = it.value
                    "password" -> password = it.value
                }
            }

            userSignUpUseCase(
                User(
                login = login!!,
                name = name!!,
                surname = surname!!
                ),
                password!!
            ).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user as Domain))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    post(UPDATE_CHATS_USER) {
        try {
            var chatId: Long? = null
            var token: String? = null

            val multipart = call.receiveMultipart().readAllParts()
            multipart.filterIsInstance<PartData.FormItem>().forEach {
                when (it.name) {
                    "chatId" -> chatId = it.value.toLongOrNull()
                    "token" -> token = it.value
                }
            }


            userUpdateChatIdsUseCase(token!!, chatId!!).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user as Domain))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
            call.respond(RespondData<String>(message = e.message))
        }
    }

    post(UPDATE_PASSWORD) {
        try {
            var password: String? = null
            var token: String? = null

            val multipart = call.receiveMultipart().readAllParts()
            multipart.filterIsInstance<PartData.FormItem>().forEach {
                when (it.name) {
                    "token" -> token = it.value
                    "newPassword" -> password = it.value
                }
            }


            userUpdatePasswordUseCase(token!!, password!!).collect {
                when (it) {
                    is State.Success -> {
                        it.data?.let { user ->
                            call.respond(RespondData(user as Domain))
                        } ?: DataIsNull()
                    }

                    is State.Error -> throw Exception(it.message)

                    else -> {}
                }
            }
        } catch (e: Exception) {
        call.respond(RespondData<String>(message = e.message))
    }
    }

}