package presentation.features.util.widgets.text_fields

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.Message
import presentation.style.icons.filled.IconFilledSend
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun MessageInputTextField(
    backgroundColor: Color,
    fontColor: Color,
    replyMessage: Message? = null,
    replyMessageColor: Color,
    onSend: (String) -> Unit,
    onReplyClose: () -> Unit
) {

    var textFieldValue: String by remember { mutableStateOf("") }


    Column {

        replyMessage?.let { message ->
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row {

                    Spacer(Modifier.size(16.dp))

                    Spacer(Modifier.height(38.dp).width(4.dp).background(replyMessageColor, RoundedCornerShape(50)))

                    Column(
                        modifier = Modifier.padding(12.dp, 0.dp).height(42.dp).fillMaxWidth(0.7f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(message.sender, style = Typography.headlineSmall, color = replyMessageColor)

                        Text(
                            message.text,
                            overflow = TextOverflow.Ellipsis,
                            style = Typography.labelSmall,
                            modifier = Modifier.heightIn(50.dp).fillMaxWidth(),
                            softWrap = false,
                            maxLines = 2
                        )

                    }

                }

                IconButton(onClick = { onReplyClose() }) {
                    Icon(Icons.Filled.Close, null)
                }
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                modifier = Modifier
                    .heightIn(56.dp, 284.dp)
                    .background(
                        backgroundColor,
                        RoundedCornerShape(16.dp)
                    )
                    .align(CenterVertically)
                    .weight(1f),
                enabled = true,
                textStyle = Typography.bodyLarge.copy(color = fontColor),
                cursorBrush = SolidColor(fontColor),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(CenterVertically)
                                .padding(16.dp)
                        ) {
                            innerTextField()
                        }
                    }
                }
            )

            Spacer(Modifier.size(8.dp))

            FilledIconButton(
                modifier = Modifier
                    .align(Top)
                    .size(56.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onSend(textFieldValue)
                    textFieldValue = ""
                }
            ) {
                Icon(IconFilledSend, null)
            }
        }
    }

}