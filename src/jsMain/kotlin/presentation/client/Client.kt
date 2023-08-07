package presentation.client

import core.di.apiModule
import core.di.clientModule
import core.di.controllerModule
import core.di.useCaseModule
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import presentation.features.application.ClientMainApp
import presentation.features.application.components.CanvasSizeWindow
import presentation.style.strings.Strings
import presentation.style.strings.Strings.appName


fun main() {

    startKoin {
        modules(
            listOf(
                clientModule,
                useCaseModule,
                apiModule,
                controllerModule
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
