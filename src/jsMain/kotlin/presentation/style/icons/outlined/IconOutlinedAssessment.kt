package presentation.style.icons.outlined

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val IconOutlinedAssessment: ImageVector
    get() {
        if (_assessment != null) {
            return _assessment!!
        }
        _assessment = materialIcon(name = "Outlined.Assessment") {
            materialPath {
                moveTo(19.0f, 3.0f)
                lineTo(5.0f, 3.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(21.0f, 5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(19.0f, 19.0f)
                lineTo(5.0f, 19.0f)
                lineTo(5.0f, 5.0f)
                horizontalLineToRelative(14.0f)
                verticalLineToRelative(14.0f)
                close()
                moveTo(7.0f, 10.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(7.0f)
                lineTo(7.0f, 17.0f)
                close()
                moveTo(11.0f, 7.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(10.0f)
                horizontalLineToRelative(-2.0f)
                close()
                moveTo(15.0f, 13.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }
        return _assessment!!
    }

private var _assessment: ImageVector? = null