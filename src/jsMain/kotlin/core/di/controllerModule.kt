package core.di

import domain.controller.SettingsController
import org.koin.dsl.module
import presentation.features.application.controller.SettingsControllerImpl
import presentation.features.chat.controller.ClientChatController

val controllerModule = module {
    single<SettingsController> { SettingsControllerImpl() }

    single { ClientChatController() }
}