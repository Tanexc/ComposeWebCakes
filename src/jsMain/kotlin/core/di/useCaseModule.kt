package core.di

import domain.use_case.*
import domain.use_case.client.GetClientIdUseCase
import domain.use_case.client.GetClientNameUseCase
import domain.use_case.client.SetClientNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetClientNameUseCase() }
    single { GetMessagesUseCase() }
    single { GetClientIdUseCase() }
    single { SetClientNameUseCase() }
    single { PostMessageUseCase() }
}