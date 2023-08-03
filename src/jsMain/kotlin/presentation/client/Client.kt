package presentation.client

import core.di.clientModule
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.features.application.components.BrowserViewportWindow
import presentation.style.strings.Strings
import presentation.style.strings.Strings.EN.appName
import presentation.style.ui.theme.ClientTheme


fun main() {

    startKoin {
        modules(clientModule)
    }

    onWasmReady {
        BrowserViewportWindow(Strings.EN(appName)) {
            ClientTheme(true) {
                ClientMainApp()
            }
        }
    }
}
