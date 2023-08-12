package core.di

import data.repository.ChatRepositoryImpl
import data.repository.MessageRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.ChatRepository
import domain.repository.MessageRepository
import domain.repository.UserRepository
import domain.use_case.chat.ChatCreateUseCase
import domain.use_case.chat.ChatGetAllUseCase
import domain.use_case.chat.ChatGetByClientIdUseCase
import domain.use_case.message.MessageGetByClientIdUseCase
import domain.use_case.message.MessageGetByIdUseCase
import domain.use_case.user.*
import org.koin.dsl.module

val useCaseModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<ChatRepository> { ChatRepositoryImpl() }
    single<MessageRepository> { MessageRepositoryImpl() }

    single { ChatGetByClientIdUseCase() }
    single { ChatCreateUseCase() }
    single { ChatGetAllUseCase() }

    single { UserGetByIdUseCase() }
    single { UserSignInUseCase() }
    single { UserSignUpUseCase() }
    single { UserGetByTokenUseCase() }
    single { UserUpdatePasswordUseCase() }
    single { UserUpdateChatIdsUseCase() }

    single { MessageGetByIdUseCase() }
    single { MessageGetByClientIdUseCase() }
}