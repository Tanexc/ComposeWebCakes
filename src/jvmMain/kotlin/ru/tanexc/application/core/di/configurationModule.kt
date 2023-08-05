package ru.tanexc.application.core.di

import org.koin.dsl.module
import ru.tanexc.application.core.util.config.ConfigTool
import ru.tanexc.application.presentation.features.websocket.controller.ChatConnectionController

val configurationModule = module {
    single { ConfigTool() }

    single<ChatConnectionController> {
        ChatConnectionController()
    }
}