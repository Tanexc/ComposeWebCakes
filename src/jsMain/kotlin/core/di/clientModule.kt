package core.di

import data.api.chat.ChatApiImpl
import data.api.user.UserApiImpl
import data.repository.ClientRepositoryImpl
import domain.interfaces.ChatApi
import domain.interfaces.UserApi
import domain.repository.ClientRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module

val clientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }

            install(WebSockets) {
                pingInterval = 100
                maxFrameSize = Long.MAX_VALUE
            }
        }
    }

    single<ClientRepository> {
        ClientRepositoryImpl()
    }

    single<UserApi> { UserApiImpl() }
    single<ChatApi> { ChatApiImpl() }
}