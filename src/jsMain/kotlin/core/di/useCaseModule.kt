package core.di

import data.repository.ChatRepositoryImpl
import data.repository.MessageRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.ChatRepository
import domain.repository.MessageRepository
import domain.repository.UserRepository
import domain.use_case.client.GetClientIdUseCase
import domain.use_case.client.GetClientNameUseCase
import domain.use_case.client.SetClientNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<ChatRepository> { ChatRepositoryImpl() }
    single<MessageRepository> { MessageRepositoryImpl() }

    single { GetClientNameUseCase() }
    single { GetClientIdUseCase() }
    single { SetClientNameUseCase() }
}