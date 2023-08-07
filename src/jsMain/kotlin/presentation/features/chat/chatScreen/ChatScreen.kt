package presentation.features.chat.chatScreen

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.core.context.GlobalContext
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ClientChatController
import presentation.style.icons.filled.IconFilledSend
import presentation.style.strings.Strings.typeMessage
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun ChatScreen() {
    val controller: ClientChatController by GlobalContext.get().inject()

    val messageText: MutableState<String> = remember {mutableStateOf("")}
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(controller.messageList.size) {
        if (controller.messageList.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {

            Row(
                modifier = Modifier
                    .widthIn(max = 804.dp)
                    .height(720.dp)
                    .padding(6.dp)
                    .background(
                        applicationColorScheme.secondaryContainer.copy(0.3f),
                        RoundedCornerShape(4.dp)
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyColumnState,
                        reverseLayout = true
                    ) {
                        items(controller.messageList) { messageItem ->
                            MessageBubble(
                                message = messageItem,
                                modifier = Modifier
                                    .background(
                                        applicationColorScheme.secondaryContainer.copy(0.6f),
                                        when (controller.messageList.indexOf(messageItem)) {
                                            (controller.messageList.lastIndex) -> if (controller.clientId.toString() == messageItem.sender) {
                                                RoundedCornerShape(22.dp, 22.dp, 6.dp, 22.dp)
                                            } else {
                                                RoundedCornerShape(22.dp, 22.dp, 22.dp, 6.dp)
                                            }

                                             0 -> if (controller.clientId.toString() == messageItem.sender) {
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
                }
            }

            Row(
                modifier = Modifier
                    .widthIn(max = 804.dp)
                    .wrapContentHeight()
                    .padding(6.dp)
            ) {
                OutlinedTextField(
                    value = messageText.value,
                    onValueChange = { messageText.value = it },
                    modifier = Modifier
                        .heightIn(48.dp, 256.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(applicationResources(typeMessage))
                    },
                    label = null,
                    trailingIcon = {
                        Icon(IconFilledSend,
                            null,
                            modifier = Modifier
                                .clickable {
                                    controller.sendMessage(messageText.value)
                                    messageText.value = ""
                                }
                        )
                    }
                )
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = lazyColumnState
            ),
            reverseLayout = true
        )

    }
}