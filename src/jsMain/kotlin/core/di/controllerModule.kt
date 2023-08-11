package core.di

import org.koin.dsl.module
import presentation.features.chat.controller.ChatController
import presentation.features.chat.controller.ClientChatController
import presentation.features.settings.controller.SettingsController

val controllerModule = module {
    single { ClientChatController() }
    single { ChatController() }
    single { SettingsController() }
}