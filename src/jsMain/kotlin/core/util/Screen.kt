package core.util

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

    object Chat: Screen(
        label = Strings.VALUES.chat,
        iconOutlined = IconOutlinedChat,
        iconFilled = IconFilledChat
    )

    object Info: Screen(
        label = Strings.VALUES.aboutUs,
        iconOutlined = IconOutlinedAssessment,
        iconFilled = IconFilledAssessment
    )

    object FeedBack: Screen(
        label = Strings.VALUES.feedback,
        iconOutlined = IconOutlinedThumbUp,
        iconFilled = IconFilledThumbUp
    )


}