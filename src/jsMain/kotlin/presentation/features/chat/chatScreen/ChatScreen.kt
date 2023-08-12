package presentation.features.chat.chatScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.util.Dialogs
import domain.model.User
import org.koin.core.context.GlobalContext
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ChatController
import presentation.features.util.shapes.Shape
import presentation.features.util.widgets.dialogs.BaseDialog
import presentation.style.icons.filled.IconFilledSend
import presentation.style.icons.rounded.IconRoundedCake
import presentation.style.strings.Strings
import presentation.style.strings.Strings.typeMessage
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@OptIn(ExperimentalStdlibApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    user: User
) {
    val controller: ChatController by GlobalContext.get().inject()
    controller.initialize(user)

    var messageTextValue by remember { mutableStateOf("") }
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(controller.messageList.size) {
        if (controller.messageList.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {

            if (controller.chatList != null) {
                Row {
                    LazyRow {
                        items(controller.chatList!!) {
                            val colorHex = it.title!!.split("#")[1].substring(0, 6)
                            Column(modifier = Modifier
                                .size(88.dp)
                                .padding(4.dp)
                                .clickable {
                                    controller.connectToChat(it)
                                }
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .background(
                                            Color(
                                                red = colorHex.substring(0, 2).hexToInt(),
                                                green = colorHex.substring(2, 4).hexToInt(),
                                                blue = colorHex.substring(4, 6).hexToInt()
                                            ),
                                            CircleShape
                                        )
                                        .size(56.dp)
                                        .padding(8.dp)
                                        .align(CenterHorizontally)
                                )
                                Text(
                                    it.title,
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = true,
                                    modifier = Modifier.align(CenterHorizontally).padding(4.dp).basicMarquee(),
                                    style = Typography.labelSmall
                                )
                            }

                        }
                    }
                }
            } else {
                controller.getAllChats()
            }



            if (controller.chat != null) {

                Row(
                    modifier = Modifier
                        .widthIn(max = 804.dp)
                        .height(640.dp)
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
                                    .align(Alignment.Center)
                                    .size(48.dp)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                state = lazyColumnState,
                                reverseLayout = true
                            ) {
                                items(controller.messageList) { messageItem ->

                                    val bubbleShape: RoundedCornerShape =
                                        when (controller.messageList.indexOf(messageItem)) {
                                            controller.messageList.lastIndex -> {
                                                if (controller.user!!.name == messageItem.sender)
                                                    Shape.LastMessageShapeEnd()
                                                else
                                                    Shape.LastMessageShapeStart()
                                            }

                                            0 -> {
                                                if (controller.user!!.name == messageItem.sender)
                                                    Shape.FirstMessageShapeEnd()
                                                else
                                                    Shape.FirstMessageShapeStart()
                                            }

                                            else -> {
                                                if (controller.user!!.name == messageItem.sender) {
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

                                    MessageBubble(
                                        message = messageItem,
                                        modifier = Modifier
                                            .background(
                                                applicationColorScheme.secondaryContainer.copy(0.6f),
                                                bubbleShape
                                            ),
                                        align = if (controller.user!!.name == messageItem.sender) {
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
                            Column(modifier = Modifier.align(TopCenter).padding(4.dp)) {

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
                                        applicationResources(Strings.appName),
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically)
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
                        value = messageTextValue,
                        onValueChange = { messageTextValue = it },
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
                                        controller.sendMessage(messageTextValue)
                                        messageTextValue = ""
                                    }
                            )
                        }
                    )
                }
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