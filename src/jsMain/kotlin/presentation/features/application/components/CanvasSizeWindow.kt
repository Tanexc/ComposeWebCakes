package presentation.features.application.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document
import org.w3c.dom.HTMLStyleElement


@OptIn(ExperimentalComposeUiApi::class)
fun CanvasSizeWindow(
    title: String = "Untitled",
    canvasElementId: String = "ComposeTarget",
    content: @Composable () -> Unit,
) {
    val htmlHeadElement = document.head!!
    htmlHeadElement.appendChild(
        (document.createElement("style") as HTMLStyleElement).apply {
            type = "text/css"
            appendChild(
                document.createTextNode(
                    """
                    html, body {
                        overflow: hidden;
                        margin: 0 !important;
                        padding: 0 !important;
                    }

                    #$canvasElementId {
                        height: auto !important;
                        outline: none;
                    }
                    """.trimIndent(),
                ),
            )
        },
    )

    CanvasBasedWindow(title = title, canvasElementId = canvasElementId, content = content)

}