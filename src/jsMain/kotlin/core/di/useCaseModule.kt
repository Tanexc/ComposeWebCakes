package core.di

import data.repository.ChatRepositoryImpl
import data.repository.MessageRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.ChatRepository
import domain.repository.MessageRepository
import domain.repository.UserRepository
import org.koin.dsl.module

val useCaseModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<ChatRepository> { ChatRepositoryImpl() }
    single<MessageRepository> { MessageRepositoryImpl() }
}