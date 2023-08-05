package presentation.features.chat.chatScreen

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ClientChatController
import presentation.style.icons.filled.IconFilledSend

@Composable
fun ClientChatScreen() {
    val controller = ClientChatController()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {

            Row(
                modifier = Modifier
                    .width(1280.dp)
                    .height(720.dp)
                    .padding(6.dp)
                    .background(
                        controller.settings.theme.secondaryContainer.copy(0.3f),
                        RoundedCornerShape(4.dp)
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(controller.messageList) { messageItem ->

                            MessageBubble(
                                message = messageItem,
                                modifier = Modifier
                                    .background(
                                        controller.settings.theme.secondaryContainer.copy(0.6f),
                                        when (controller.messageList.indexOf(messageItem)) {
                                            0 -> if (controller.clientId.toString() == messageItem.sender) {
                                                RoundedCornerShape(22.dp, 22.dp, 6.dp, 22.dp)
                                            } else {
                                                RoundedCornerShape(22.dp, 22.dp, 22.dp, 6.dp)
                                            }

                                            (controller.messageList.lastIndex) -> if (controller.clientId.toString() == messageItem.sender) {
                                                RoundedCornerShape(22.dp, 6.dp, 22.dp, 22.dp)
                                            } else {
                                                RoundedCornerShape(6.dp, 22.dp, 22.dp, 22.dp)
                                            }

                                            else -> if (controller.clientId.toString() == messageItem.sender) {
                                                RoundedCornerShape(22.dp, 6.dp, 6.dp, 22.dp)
                                            } else {
                                                RoundedCornerShape(6.dp, 22.dp, 22.dp, 6.dp)
                                            }
                                        }
                                    ),
                                align = if (controller.clientId.toString() == messageItem.sender) {
                                    Alignment.CenterEnd
                                } else {
                                    Alignment.CenterStart
                                }
                            )
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = controller.lazyColumnState
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .width(1280.dp)
                    .wrapContentHeight()
                    .padding(6.dp)
            ) {
                OutlinedTextField(
                    value = controller.textFieldValue,
                    onValueChange = { controller.updateTextFieldValue(it) },
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