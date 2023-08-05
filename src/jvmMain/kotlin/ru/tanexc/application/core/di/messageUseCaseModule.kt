package ru.tanexc.application.core.di

import org.koin.dsl.module
import ru.tanexc.application.domain.use_cases.message_use_cases.MessageCreateUseCase
import ru.tanexc.application.domain.use_cases.message_use_cases.MessageGetByIdUseCase
import ru.tanexc.application.domain.use_cases.message_use_cases.MessageGetByListUseCase

val messageUseCaseModule = module {
    single {
        MessageGetByIdUseCase()
    }

    single {
        MessageCreateUseCase()
    }

    single {
        MessageGetByListUseCase()
    }
}