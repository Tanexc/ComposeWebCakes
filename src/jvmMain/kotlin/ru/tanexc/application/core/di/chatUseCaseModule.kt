package ru.tanexc.application.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatCreateUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatGetByClientIdUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatGetByIdUseCase
import ru.tanexc.application.domain.use_cases.chat_use_cases.ChatInsertMessageUseCase

val chatUseCaseModule = module {
    singleOf(::ChatCreateUseCase) {
        ChatCreateUseCase()
    }

    singleOf(::ChatGetByIdUseCase) {
        ChatGetByIdUseCase()
    }

    singleOf(::ChatInsertMessageUseCase) {
        ChatInsertMessageUseCase()
    }

    singleOf(::ChatGetByClientIdUseCase) {
        ChatGetByClientIdUseCase()
    }
}