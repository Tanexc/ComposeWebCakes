package presentation.client

import androidx.compose.ui.ExperimentalComposeUiApi
import core.di.clientModule
import core.di.controllerModule
import core.di.useCaseModule
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.features.application.components.CanvasSizeWindow
import presentation.style.strings.Strings
import presentation.style.strings.Strings.EN.appName
import presentation.style.ui.theme.ClientTheme


fun main() {

    startKoin {
        modules(listOf(
            clientModule,
            controllerModule,
            useCaseModule
        ))
    }

    onWasmReady {
        CanvasSizeWindow(
            title = Strings.EN(appName),
            canvasElementId = "ComposeTarget"
        ) {
            ClientTheme(true) {
                ClientMainApp()
            }
        }
    }
}
