package presentation.features.util.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

sealed class Shape {

    data object FirstMessageShapeEnd {
        operator fun invoke() = RoundedCornerShape(22.dp, 6.dp, 22.dp, 22.dp)
    }

    data object FirstMessageShapeStart {
        operator fun invoke() = RoundedCornerShape(6.dp, 22.dp, 22.dp, 22.dp)
    }

    data object MiddleMessageShapeEnd {
        operator fun invoke() = RoundedCornerShape(22.dp, 6.dp, 6.dp, 22.dp)
    }

    data object MiddleMessageShapeStart {
        operator fun invoke() = RoundedCornerShape(6.dp, 22.dp, 22.dp, 6.dp)
    }

    data object LastMessageShapeEnd {
        operator fun invoke() = RoundedCornerShape(22.dp, 22.dp, 6.dp, 22.dp)
    }

    data object LastMessageShapeStart {
        operator fun invoke() = RoundedCornerShape(22.dp, 22.dp, 22.dp, 6.dp)
    }


}