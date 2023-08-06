package presentation.client

import core.di.clientModule
import core.di.useCaseModule
import core.util.HashTool
import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.features.application.components.CanvasSizeWindow
import presentation.style.strings.Strings
import presentation.style.strings.Strings.appName
import presentation.style.ui.theme.ClientTheme
import presentation.style.ui.theme.applicationUseDarkTheme


fun main() {

    startKoin {
        modules(
            listOf(
                clientModule,
                useCaseModule
            )
        )
    }

    onWasmReady {
        CanvasSizeWindow(
            title = Strings.EN(appName),
            canvasElementId = "ComposeTarget"
        ) {

            ClientMainApp()

        }
    }
}
