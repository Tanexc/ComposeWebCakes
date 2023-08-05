package core.di

import domain.use_case.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetClientNameUseCase() }
    single { GetMessagesUseCase() }
    single { GetClientIdUseCase() }
    single { SetClientNameUseCase() }
    single { PostMessageUseCase() }
}