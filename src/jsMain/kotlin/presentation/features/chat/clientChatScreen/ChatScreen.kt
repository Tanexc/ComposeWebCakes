package presentation.features.chat.clientChatScreen


import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.core.context.GlobalContext
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ClientChatController
import presentation.features.util.shapes.Shape
import presentation.style.icons.filled.IconFilledSend
import presentation.style.icons.rounded.IconRoundedCake
import presentation.style.strings.Strings
import presentation.style.strings.Strings.appName
import presentation.style.strings.Strings.nameWarning
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun ChatScreen() {
    val controller: ClientChatController by GlobalContext.get().inject()
    controller.updateName()
    controller.initializeChat()

    val messageText: MutableState<String> = remember { mutableStateOf("") }
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(controller.messageList.size) {
        if (controller.messageList.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (controller.showInsertNameDialog) {

            Column(modifier = Modifier.align(Center)) {
                Icon(
                    Icons.Outlined.Warning,
                    null,
                    modifier = Modifier.size(56.dp).align(CenterHorizontally)
                )
                Text(applicationResources(nameWarning), textAlign = TextAlign.Center)
            }

        } else {
            Column(modifier = Modifier.align(Center)) {

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

                        if (controller.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Center)
                                    .size(48.dp)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                state = lazyColumnState,
                                reverseLayout = true
                            ) {
                                items(controller.messageList) { messageItem ->

                                    val bubbleShape: RoundedCornerShape = when (controller.messageList.indexOf(messageItem)) {
                                        0 -> {
                                                if (controller.clientName.toString() == messageItem.sender)
                                                    Shape.FirstMessageShapeEnd()
                                                else
                                                    Shape.FirstMessageShapeStart()
                                        }

                                        controller.messageList.lastIndex -> {
                                            if (controller.clientName.toString() == messageItem.sender)
                                                Shape.LastMessageShapeEnd()
                                            else
                                                Shape.LastMessageShapeStart()
                                        }

                                        else -> {
                                            if (controller.clientName.toString() == messageItem.sender) {
                                                if (controller.messageList[controller.messageList.indexOf(messageItem) + 1].sender != messageItem.sender) {
                                                    Shape.LastMessageShapeEnd()
                                                } else if (controller.messageList[controller.messageList.indexOf(messageItem) - 1].sender != messageItem.sender) {
                                                    Shape.FirstMessageShapeEnd()
                                                } else {
                                                    Shape.MiddleMessageShapeEnd()
                                                }
                                            } else {
                                                if (controller.messageList[controller.messageList.indexOf(messageItem) + 1].sender != messageItem.sender) {
                                                    Shape.LastMessageShapeStart()
                                                } else if (controller.messageList[controller.messageList.indexOf(messageItem) - 1].sender != messageItem.sender) {
                                                    Shape.FirstMessageShapeStart()
                                                }  else {
                                                    Shape.MiddleMessageShapeStart()
                                                }
                                            }
                                        }
                                    }

                                    MessageBubble(
                                        message = messageItem,
                                        modifier = Modifier
                                            .background(
                                                applicationColorScheme.secondaryContainer.copy(0.6f),
                                                bubbleShape
                                            ),
                                        align = if (controller.clientName.toString() == messageItem.sender) {
                                            Alignment.CenterEnd
                                        } else {
                                            Alignment.CenterStart
                                        }
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.Companion.verticalGradient(
                                        listOf(
                                            applicationColorScheme.secondaryContainer.copy(0.3f),
                                            applicationColorScheme.secondaryContainer.copy(0f)
                                        )
                                    ),
                                )
                        ) {
                            Column(modifier = Modifier.align(Alignment.TopCenter).padding(4.dp)) {

                                Row(modifier = Modifier.align(CenterHorizontally)) {
                                    Box(
                                        modifier = Modifier.background(
                                            applicationColorScheme.primary,
                                            CircleShape
                                        )
                                            .size(56.dp)
                                    ) {
                                        Icon(
                                            IconRoundedCake,
                                            null,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(12.dp)
                                        )
                                    }

                                    Text(
                                        applicationResources(appName),
                                        modifier = Modifier.padding(8.dp).align(CenterVertically)
                                    )

                                }

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
                            Text(applicationResources(Strings.typeMessage))
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
}