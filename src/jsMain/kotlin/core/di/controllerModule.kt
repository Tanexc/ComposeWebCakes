package core.di

import org.koin.dsl.module
import presentation.features.application.controller.SettingsControllerImpl
import presentation.features.chat.controller.ChatController

val controllerModule = module {
    single {
        SettingsControllerImpl()
    }

    single {
        ChatController()
    }
}