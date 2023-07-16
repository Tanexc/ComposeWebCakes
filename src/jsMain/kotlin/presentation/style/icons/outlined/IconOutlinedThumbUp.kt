package presentation.style.icons.outlined

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val IconOutlinedThumbUp: ImageVector
    get() {
        if (_thumbUp != null) {
            return _thumbUp!!
        }
        _thumbUp = materialIcon(name = "Outlined.ThumbUp") {
            materialPath {
                moveTo(9.0f, 21.0f)
                horizontalLineToRelative(9.0f)
                curveToRelative(0.83f, 0.0f, 1.54f, -0.5f, 1.84f, -1.22f)
                lineToRelative(3.02f, -7.05f)
                curveToRelative(0.09f, -0.23f, 0.14f, -0.47f, 0.14f, -0.73f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-6.31f)
                lineToRelative(0.95f, -4.57f)
                lineToRelative(0.03f, -0.32f)
                curveToRelative(0.0f, -0.41f, -0.17f, -0.79f, -0.44f, -1.06f)
                lineTo(14.17f, 1.0f)
                lineTo(7.58f, 7.59f)
                curveTo(7.22f, 7.95f, 7.0f, 8.45f, 7.0f, 9.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(9.0f, 9.0f)
                lineToRelative(4.34f, -4.34f)
                lineTo(12.0f, 10.0f)
                horizontalLineToRelative(9.0f)
                verticalLineToRelative(2.0f)
                lineToRelative(-3.0f, 7.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(9.0f)
                close()
                moveTo(1.0f, 9.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(12.0f)
                horizontalLineTo(1.0f)
                close()
            }
        }
        return _thumbUp!!
    }

private var _thumbUp: ImageVector? = null
