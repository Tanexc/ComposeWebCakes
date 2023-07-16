package presentation.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.model.Message
import presentation.features.chat.components.MessageBubble
import presentation.style.icons.filled.IconFilledSend
import presentation.style.ui.theme.getTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val theme = getTheme(true)
    val message: MutableState<String> = remember { mutableStateOf("") }
    val userId = 0L

    val messagesList: List<Message> = listOf()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {

            Row(
                modifier = Modifier
                    .width(1280.dp)
                    .height(720.dp)
                    .padding(6.dp)
                    .background(
                        theme.secondaryContainer.copy(0.3f),
                        RoundedCornerShape(4.dp)
                    )
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(messagesList) { messageItem ->

                        MessageBubble(
                            message = messageItem,
                            modifier = Modifier
                                .background(
                                    theme.secondaryContainer.copy(0.6f),
                                    when (messagesList.indexOf(messageItem)) {
                                        0 -> if (userId == messageItem.sender) {
                                            RoundedCornerShape(22.dp, 22.dp, 6.dp, 22.dp)
                                        } else {
                                            RoundedCornerShape(22.dp, 22.dp, 22.dp, 6.dp)
                                        }

                                        (messagesList.lastIndex) -> if (userId == messageItem.sender) {
                                            RoundedCornerShape(22.dp, 6.dp, 22.dp, 22.dp)
                                        } else {
                                            RoundedCornerShape(6.dp, 22.dp, 22.dp, 22.dp)
                                        }

                                        else -> if (userId == messageItem.sender) {
                                            RoundedCornerShape(22.dp, 6.dp, 6.dp, 22.dp)
                                        } else {
                                            RoundedCornerShape(6.dp, 22.dp, 22.dp, 6.dp)
                                        }
                                    }
                                ),
                            align = if (userId == messageItem.sender) {
                                Alignment.CenterEnd
                            } else {
                                Alignment.CenterStart
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .width(1280.dp)
                    .wrapContentHeight()
                    .padding(6.dp)
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    modifier = Modifier
                        .heightIn(48.dp, 256.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Type message...")
                    },
                    label = null,
                    trailingIcon = {
                        Icon(IconFilledSend, null)
                    }
                )
            }
        }


    }
}