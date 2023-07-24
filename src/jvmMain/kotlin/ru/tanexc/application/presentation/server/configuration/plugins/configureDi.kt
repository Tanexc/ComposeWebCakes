package ru.tanexc.application.presentation.server.configuration.plugins


import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import ru.tanexc.application.core.di.databaseModule

fun Application.configureDi() {
    install(Koin) {
        module {
            databaseModule
        }
    }
}