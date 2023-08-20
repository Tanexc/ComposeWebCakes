package presentation.features.chat.clientChatScreen


import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.util.Dialogs
import domain.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ClientChatController
import presentation.features.util.shapes.Shape
import presentation.features.util.widgets.dialogs.BaseDialog
import presentation.features.util.widgets.text_fields.MessageInputTextField
import presentation.style.icons.filled.IconFilledSend
import presentation.style.icons.rounded.IconRoundedCake
import presentation.style.strings.Strings
import presentation.style.strings.Strings.appName
import presentation.style.strings.Strings.nameWarning
import presentation.style.strings.Strings.typeName
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ChatScreen() {
    val controller: ClientChatController by GlobalContext.get().inject()
    controller.updateName()
    controller.initializeChat()

    val replyMessage: MutableState<Message?> = remember { mutableStateOf(null) }

    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(controller.messageList.size) {
        if (controller.messageList.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (controller.clientName != null) {
            Column(modifier = Modifier.widthIn(max = 800.dp).align(Alignment.TopCenter)) {
                if (controller.chat != null) {

                    val chatColorHex = controller.chat!!.title!!.split("#")[1].substring(0, 6)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.align(Center).padding(4.dp)) {

                            Row(modifier = Modifier.align(CenterHorizontally)) {
                                Box(
                                    modifier = Modifier.background(
                                        color = applicationColorScheme.primary,
                                        CircleShape
                                    )
                                        .size(56.dp)
                                )

                                Text(
                                    applicationResources(appName),
                                    modifier = Modifier.padding(8.dp).align(CenterVertically)
                                )

                            }

                            Spacer(Modifier.size(8.dp))
                        }

                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                applicationColorScheme.outline,
                                RoundedCornerShape(50)
                            )
                    )

                    if (controller.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .size(48.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().weight(1f),
                            state = lazyColumnState,
                            reverseLayout = true
                        ) {
                            items(controller.messageList) { messageItem ->

                                val bubbleShape: RoundedCornerShape =
                                    when (controller.messageList.indexOf(messageItem)) {
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
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeEnd()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeEnd()
                                                } else {
                                                    Shape.MiddleMessageShapeEnd()
                                                }
                                            } else {
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeStart()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeStart()
                                                } else {
                                                    Shape.MiddleMessageShapeStart()
                                                }
                                            }
                                        }
                                    }

                                var replyTo: Message? by remember { mutableStateOf(null) }
                                CoroutineScope(Dispatchers.Default).launch {
                                    replyTo = controller.getMessageById(messageItem.replyTo ?: -1)
                                }

                                MessageBubble(
                                    message = messageItem,
                                    shape = bubbleShape,
                                    modifier = Modifier
                                        .background(
                                            applicationColorScheme.secondaryContainer.copy(0.6f),
                                            bubbleShape
                                        ),
                                    arrangement = if (controller.clientName.toString() == messageItem.sender) {
                                        Arrangement.End
                                    } else {
                                        Arrangement.Start
                                    },
                                    withName = controller.clientName != messageItem.sender,
                                    nameColor = applicationColorScheme.primary,
                                    replyTo = replyTo,
                                    onReply = { replyMessage.value = it }
                                )
                            }
                        }
                    }


                    Spacer(Modifier.size(8.dp))

                    MessageInputTextField(
                        backgroundColor = applicationColorScheme.secondaryContainer.copy(0.3f),
                        fontColor = contentColorFor(applicationColorScheme.secondaryContainer.copy(0.3f)),
                        onSend = {
                            controller.sendMessage(it, replyMessage.value?.id)
                            replyMessage.value = null
                        },
                        replyMessageColor = Color(
                            red = chatColorHex.substring(0, 2).hexToInt(),
                            green = chatColorHex.substring(2, 4).hexToInt(),
                            blue = chatColorHex.substring(4, 6).hexToInt()
                        ),
                        replyMessage = replyMessage.value,
                        onReplyClose = { replyMessage.value = null }
                    )

                    Spacer(Modifier.size(16.dp))

                }
            }
            VerticalScrollbar(
                modifier = Modifier
                    .align(CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = lazyColumnState
                ),
                reverseLayout = true
            )
        } else {
            Column(modifier = Modifier.align(Center)) {
                Icon(Icons.Filled.Warning, null, modifier = Modifier.align(CenterHorizontally))
                Text(applicationResources(nameWarning), modifier = Modifier.align(CenterHorizontally), textAlign = TextAlign.Center)

            }
        }
    }

    if (controller.dialogToShow !is Dialogs.None) {
        BaseDialog(
            icon = controller.dialogToShow.icon,
            message = controller.dialogMessage,
            headlineText = controller.dialogToShow.headline
        ) {
            controller.closeDialog()
        }
    }
}
