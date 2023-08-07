package core.di

import data.api.chat.ChatApiImpl
import data.api.message.MessageApiImpl
import data.api.user.UserApiImpl
import domain.interfaces.ChatApi
import domain.interfaces.MessageApi
import domain.interfaces.UserApi
import org.koin.dsl.module

val apiModule = module {
    single<MessageApi> {
        MessageApiImpl()
    }
    single<ChatApi> {
        ChatApiImpl()
    }
    single<UserApi> {
        UserApiImpl()
    }
}