package ru.tanexc.application.core.di

import org.koin.dsl.module
import ru.tanexc.application.data.database.dao.ChatDaoImpl
import ru.tanexc.application.data.database.dao.MessageDaoImpl
import ru.tanexc.application.domain.interfaces.ChatDao
import ru.tanexc.application.domain.interfaces.MessageDao

val dataModule = module {
    single<ChatDao> { ChatDaoImpl() }
    single<MessageDao> { MessageDaoImpl() }
}