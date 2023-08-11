package ru.tanexc.application.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.application.domain.use_cases.chat_use_cases.*

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

    singleOf(::ChatGetAllUseCase) {
        ChatGetAllUseCase()
    }
}