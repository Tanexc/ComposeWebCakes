package presentation.features.chat.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Message
import io.ktor.util.date.*
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun MessageBubble(
    modifier: Modifier = Modifier,
    message: Message,
    align: Alignment
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentHeight()
                .padding(12.dp, 4.dp)
                .align(align)
        ) {

            Box(
                modifier = modifier
                    .widthIn(min = 84.dp)
                    .align(align)
            ) {

                Text(
                    message.text,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )

                Text(
                    "${GMTDate(message.timestamp).hours}:${GMTDate(message.timestamp).minutes.toString().padStart(2, '0')}",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 2.dp, end = 16.dp),
                    style = Typography.titleSmall,
                    color = contentColorFor(applicationColorScheme.secondaryContainer).copy(0.5f)
                )

            }

        }
    }
}
