package core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import presentation.style.icons.filled.IconFilledAssessment
import presentation.style.icons.filled.IconFilledChat
import presentation.style.icons.filled.IconFilledThumbUp
import presentation.style.icons.outlined.IconOutlinedAssessment
import presentation.style.icons.outlined.IconOutlinedChat
import presentation.style.icons.outlined.IconOutlinedThumbUp
import presentation.style.strings.Strings

sealed class Screen(
    val label: Int,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector
) {

    data object Chat: Screen(
        label = Strings.chat,
        iconOutlined = IconOutlinedChat,
        iconFilled = IconFilledChat
    )

    data object Info: Screen(
        label = Strings.aboutUs,
        iconOutlined = IconOutlinedAssessment,
        iconFilled = IconFilledAssessment
    )

    data object Settings: Screen(
        label = Strings.settings,
        iconOutlined = Icons.Outlined.Settings,
        iconFilled = Icons.Filled.Settings
    )


}