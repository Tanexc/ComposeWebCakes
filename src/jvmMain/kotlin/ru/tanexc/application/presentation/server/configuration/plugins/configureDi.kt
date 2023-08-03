package ru.tanexc.application.presentation.server.configuration.plugins


import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import ru.tanexc.application.core.di.configurationModule
import ru.tanexc.application.core.di.dataModule

fun Application.configureDi() {
    startKoin {
        module {
            configurationModule
            dataModule
        }
    }
}