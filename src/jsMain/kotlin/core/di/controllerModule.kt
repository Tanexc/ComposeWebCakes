package core.di

import org.koin.dsl.module
import presentation.features.chat.controller.ClientChatController

val controllerModule = module {
    single { ClientChatController() }
}