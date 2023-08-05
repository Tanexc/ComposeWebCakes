package core.di

import data.repository.ClientRepositoryImpl
import domain.repository.ClientRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val clientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single<ClientRepository<HttpClient>> {
        ClientRepositoryImpl()
    }
}