package core.di

import data.ClientRepositoryImpl
import domain.repository.ClientRepository
import io.ktor.client.*
import org.koin.dsl.module

val clientModule = module {
    single {
        HttpClient()
    }

    single<ClientRepository<HttpClient>> {
        ClientRepositoryImpl()
    }
}