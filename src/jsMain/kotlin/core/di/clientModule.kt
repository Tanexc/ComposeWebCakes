package core.di

import data.repository.ClientRepositoryImpl
import domain.interfaces.ClientRepository
import io.ktor.client.*
import org.koin.dsl.module

val clientModule = module {
    single {
        HttpClient()
    }

    single<ClientRepository> {
        ClientRepositoryImpl()
    }
}