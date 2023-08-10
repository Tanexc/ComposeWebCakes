package core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import presentation.style.strings.Strings
import presentation.style.strings.applicationResources

sealed class Dialogs(
    val icon: ImageVector,
    val headline: String
) {
    data object Error: Dialogs(
        Icons.Outlined.Warning,
        applicationResources(Strings.error)
    )
    data object Success: Dialogs(
        Icons.Outlined.Check,
        applicationResources(Strings.enter)
    )
    data object None: Dialogs(
        Icons.Outlined.Info,
        applicationResources(Strings.appName)
    )
}