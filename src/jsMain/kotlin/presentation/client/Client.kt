package presentation.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import core.di.clientModule
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.features.application.components.BrowserViewportWindow
import presentation.features.application.components.CanvasSizeWindow
import presentation.style.strings.Strings
import presentation.style.strings.Strings.EN.appName
import presentation.style.ui.theme.ClientTheme


@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    startKoin {
        modules(clientModule)
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
