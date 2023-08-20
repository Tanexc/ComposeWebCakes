package presentation.features.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.Message
import io.ktor.util.date.*
import presentation.features.util.shapes.Shape
import presentation.style.icons.filled.IconFilledReply
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun MessageBubble(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    message: Message,
    arrangement: Arrangement.Horizontal,
    withName: Boolean = false,
    nameColor: Color? = null,
    replyTo: Message? = null,
    onReply: (Message) -> Unit
) {
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentHeight()
                .padding(0.dp, 4.dp)
                .align(if (arrangement == Arrangement.Start) CenterStart else CenterEnd),
            horizontalArrangement = arrangement
        ) {

            if (arrangement == Arrangement.End) {
                Column(Modifier.fillMaxHeight().align(CenterVertically)) {
                    IconButton(
                        onClick = { onReply(message) },
                        modifier = Modifier.align(CenterHorizontally)
                    ) {
                        Icon(
                            IconFilledReply,
                            null
                        )
                    }
                }
            }


            Column(
                modifier = modifier
                    .widthIn(min = 84.dp)
            ) {

                if (withName && nameColor != null && shape == Shape.LastMessageShapeStart()) {
                    Spacer(Modifier.size(8.dp))
                    Row {
                        Text(
                            message.sender,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 16.dp, end = 16.dp),
                            color = nameColor,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Spacer(Modifier.size(16.dp))
                }

                if (replyTo != null) {

                    Row {
                        Spacer(Modifier.size(16.dp))

                        Spacer(Modifier.height(38.dp).width(4.dp).background(nameColor!!, RoundedCornerShape(50)))

                        Column(
                            modifier = Modifier.padding(12.dp, 0.dp).heightIn(max=38.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(replyTo.sender, style = Typography.headlineSmall, color = nameColor)

                            Text(replyTo.text, overflow = TextOverflow.Ellipsis, style = Typography.labelSmall, modifier = Modifier.heightIn(max=38.dp).fillMaxWidth(), softWrap = false)

                        }

                    }
                }
                Row(Modifier.align(Start)) {
                    Text(
                        message.text,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }

                Row(Modifier.align(End)) {
                    Text(
                        "${GMTDate(message.timestamp).hours}:${
                            GMTDate(message.timestamp).minutes.toString().padStart(2, '0')
                        }",
                        modifier = Modifier
                            .padding(bottom = 4.dp, end = 16.dp),
                        style = Typography.titleSmall,
                        color = contentColorFor(applicationColorScheme.secondaryContainer).copy(0.5f)
                    )
                }
            }

            if (arrangement == Arrangement.Start) {
                Column(Modifier.fillMaxHeight().align(CenterVertically)) {
                    IconButton(
                        onClick = { onReply(message) },
                        modifier = Modifier.align(CenterHorizontally)
                    ) {
                        Icon(
                            IconFilledReply,
                            null
                        )
                    }
                }
            }


        }
    }

}
