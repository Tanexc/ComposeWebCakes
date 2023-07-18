package presentation.client

import di.clientModule
import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import presentation.features.application.components.BrowserViewportWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.style.strings.Strings
import presentation.style.strings.Strings.EN.app_name
import presentation.style.ui.theme.ClientTheme


fun main() {

    startKoin {
        modules(clientModule)
    }

    onWasmReady {
        BrowserViewportWindow(Strings.EN(app_name)) {
            ClientTheme(true) {
                ClientMainApp()
            }
        }
    }
}
