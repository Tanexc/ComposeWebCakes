package ru.tanexc.application.core.di

import org.koin.dsl.module
import ru.tanexc.application.data.database.dao.ChatDaoImpl
import ru.tanexc.application.data.database.dao.MessageDaoImpl
import ru.tanexc.application.data.database.dao.UserDaoImpl
import ru.tanexc.application.data.repository.ChatRepositoryImpl
import ru.tanexc.application.data.repository.UserRepositoryImpl
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao
import ru.tanexc.application.domain.interfaces.UserDao

val dataModule = module {
    single<ChatDao> { ChatDaoImpl() }
    single<MessageDao> { MessageDaoImpl() }
    single<UserDao> { UserDaoImpl() }

    single { UserRepositoryImpl() }
    single { ChatRepositoryImpl() }
    single { MessageDaoImpl() }
}