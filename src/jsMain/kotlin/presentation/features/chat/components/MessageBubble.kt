package presentation.features.chat.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Message

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
                    .align(
                        align
                    )
            ) {

                Text(
                    message.text,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp)
                )

            }

        }
    }
}
