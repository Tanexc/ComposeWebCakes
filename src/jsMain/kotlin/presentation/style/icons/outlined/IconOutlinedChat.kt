package presentation.style.icons.outlined

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val IconOutlinedChat: ImageVector
    get() {
        if (_chat != null) {
            return _chat!!
        }
        _chat = materialIcon(name = "Outlined.Chat") {
            materialPath {
                moveTo(4.0f, 4.0f)
                horizontalLineToRelative(16.0f)
                verticalLineToRelative(12.0f)
                lineTo(5.17f, 16.0f)
                lineTo(4.0f, 17.17f)
                lineTo(4.0f, 4.0f)
                moveToRelative(0.0f, -2.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 22.0f)
                lineToRelative(4.0f, -4.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                lineTo(4.0f, 2.0f)
                close()
                moveTo(6.0f, 12.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(2.0f)
                lineTo(6.0f, 14.0f)
                verticalLineToRelative(-2.0f)
                close()
                moveTo(6.0f, 9.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(2.0f)
                lineTo(6.0f, 11.0f)
                lineTo(6.0f, 9.0f)
                close()
                moveTo(6.0f, 6.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(2.0f)
                lineTo(6.0f, 8.0f)
                lineTo(6.0f, 6.0f)
                close()
            }
        }
        return _chat!!
    }

private var _chat: ImageVector? = null